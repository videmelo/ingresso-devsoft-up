# Documento de Decisão de Arquitetura (ADR)
**Projeto:** Sistema de Gestão de Eventos

Este documento define as diretrizes arquiteturais, a divisão de tarefas, a modelagem de dados e os padrões de engenharia que devem ser seguidos por toda a equipe (5 desenvolvedores) durante a construção do sistema.

---

## Parte 1: Visão Geral e Regras de Negócio

### 1.1. Configuração Inicial e Divisão da Equipe
* **Repositório:** Deve ser criado um repositório no GitHub. O controle de versão é obrigatório e todos os membros da equipe devem realizar commits organizados ao longo do desenvolvimento.
* **A Regra dos MVCs:** Para garantir a complexidade exigida, o sistema não deve ser simples. A regra estabelecida é que **cada desenvolvedor deve implementar 2 MVCs completos (Model, View e Controller)**, incluindo a persistência de dados.
  * *Divisão:* Como a equipe possui 5 pessoas, o sistema terá exatamente 10 Entidades (Models), 10 Controllers e 10 Views.

### 1.2. Os 3 CRUDs Mínimos Interligados
O sistema exige no mínimo 3 CRUDs (Cadastro, Leitura, Atualização e Deleção) que possuam relacionamento entre si.
* **CRUD 1 (Pessoas):** Gerenciamento de `Participantes` e `Organizadores`.
* **CRUD 2 (Eventos e Estrutura):** Gerenciamento de eventos (online/presencial), categorias, locais e adição de sessões específicas.
* **CRUD 3 (Inscrições/Financeiro):** Associação entre Participante e Sessão/Evento, gerando um histórico e um pagamento.

### 1.3. Persistência de Dados e Logs
* **Arquivos TXT ou JSON:** O armazenamento de dados será feito em arquivos de texto ou JSON. Em caso de fechamento do sistema, nenhum dado (ingresso, cadastro, pagamento) deve ser perdido.
* **Log do Sistema:** É obrigatório manter um arquivo de log separado para auditoria de eventos, check-ins e erros.

---

## Parte 2: Estrutura do Projeto (Arquitetura)

Utilizando os princípios de *Clean Code* e o padrão arquitetural MVC, a estrutura de pacotes deve ser estritamente modular. 

### 2.1. Árvore de Arquitetura de Pastas (O mapa físico)
Esta é a estrutura recomendada para criação na IDE:

```text
sistema_eventos/
├── src/
│   ├── model/                  # Camada de Dados (Entidades, Abstratas e Enums)
│   ├── interfaces/             # Contratos do sistema
│   ├── controller/             # Lógica de negócio e listas em memória
│   ├── view/                   # Telas e Menus no Console (Scanner)
│   ├── exceptions/             # Classes de Erros Personalizados
│   ├── util/                   # Ferramentas Gerais (ArquivoUtil, LogUtil)
│   └── Main.java               # Classe principal que roda o sistema
├── dados/                      # Diretório para salvar os arquivos .txt ou .json
├── logs/                       # Diretório para o arquivo log.txt
├── README.md                   # Documentação do projeto
└── .gitignore                  # Para ignorar arquivos de compilação
```

### 2.2. Árvore de Hierarquia (O mapa lógico)
Mostra como as classes se relacionam através de herança e quais implementam as interfaces:

```text
├── [Superclasse Abstrata] Pessoa
│   ├── [Subclasse] Organizador
│   └── [Subclasse] Participante (Implementa: Checkinavel)
│
├── [Superclasse Abstrata] Evento (Implementa: RelatorioGeravel)
│   ├── [Subclasse] EventoPresencial
│   └── [Subclasse] EventoOnline
│
├── [Entidades Independentes / Relacionais]
│   ├── Sessao (Vinculada a um Evento)
│   ├── Local (Vinculado a um EventoPresencial)
│   ├── Categoria (Vinculada a um Evento)
│   ├── Inscricao (Associa um Participante a uma Sessao)
│   ├── Pagamento (Vinculado a uma Inscricao)
│   └── Certificado (Gerado para um Participante)
```

---

## Parte 3: Dicionário de Dados e Modelagem de Classes

Abaixo está o detalhamento de como cada arquivo da camada de Modelo (`/src/model/` e `/src/interfaces/`) deve ser implementado. Cada item representa um arquivo `.java` independente.

> **Regras Gerais Obrigatórias para as Classes de Modelo:**
> 1. **Encapsulamento:** Todos os atributos devem ser `private`.
> 2. **Acesso:** Criar métodos `public get()` e `public set()` para todos os atributos.
> 3. **Construtores:** Toda classe concreta deve ter pelo menos um construtor público que inicializa seus atributos.
> 4. **Exibição:** Toda classe deve sobrescrever o método `public String toString()`.

### 3.1. Contratos e Superclasses (Arquivos Base)

#### 1. Arquivo: `Checkinavel.java` (Interface)
* **Local:** `/src/interfaces/Checkinavel.java`
* **Método:** `public boolean realizarCheckin();`
  * **O que deve fazer:** Verificar se o check-in é válido (ex: possui inscrição confirmada). Retorna `true` se bem-sucedido e altera o status interno de presença.

#### 2. Arquivo: `RelatorioGeravel.java` (Interface)
* **Local:** `/src/interfaces/RelatorioGeravel.java`
* **Método:** `public void gerarRelatorio();`
  * **O que deve fazer:** Realizar a leitura dos dados consolidados da entidade (total de inscritos, check-ins) e formatar um texto estatístico no console ou txt.

#### 3. Arquivo: `Pessoa.java` (Classe Abstrata)
* **Local:** `/src/model/Pessoa.java`
* **Atributos:** `String id`, `String nome`, `String cpf`, `String email`
* **Método:** `public abstract void exibirDadosPessoais();`
  * **O que deve fazer:** Obriga as subclasses a implementarem como seus dados devem ser exibidos no console.

#### 4. Arquivo: `Evento.java` (Classe Abstrata)
* **Local:** `/src/model/Evento.java`
* **Implementa:** `RelatorioGeravel`
* **Atributos:** `String id`, `String titulo`, `String data`, `String status`
* **Métodos:**
  * `public abstract void iniciarEvento();` (Obriga as filhas a definirem a inicialização).
  * `public void gerarRelatorio()` (Exibe no console um resumo amigável do evento atual).

### 3.2. Modelagem por Entidade Concreta (10 MVCs)

**Módulo 1: Pessoas**
* **5. `Organizador.java`** (extends Pessoa)
  * **Atributos:** `String setor`, `String nivelAcesso`
  * **Métodos Específicos:**
    * `public void exibirDadosPessoais()`: Imprime no console uma ficha formatada (ex: `[ORGANIZADOR] Nome: X | CPF: Y | Setor: Z`).
    * `public boolean aprovarEvento(Evento evento)`: Verifica se o `nivelAcesso` do organizador permite aprovação (ex: nível "Admin"). Se sim, altera o status do objeto `evento` para "Aprovado" e retorna `true`.
* **6. `Participante.java`** (extends Pessoa implements Checkinavel)
  * **Atributos:** `String matricula`, `List<Inscricao> historicoInscricoes`
  * **Métodos Específicos:**
    * `public void exibirDadosPessoais()`: Imprime no console: `[PARTICIPANTE] Nome: X | Matrícula: Y | Total Inscrições: Z`.
    * `public boolean realizarCheckin()`: Verifica se a lista `historicoInscricoes` possui alguma inscrição confirmada para a data atual. Se possuir, altera o status interno de presença e retorna `true`.

**Módulo 2: Eventos**
* **7. `EventoPresencial.java`** (extends Evento)
  * **Atributos:** `Local local`, `int capacidadeMaxima`
  * **Métodos Específicos:**
    * `public void iniciarEvento()`: Altera o `status` (herdado) para "Em andamento" e imprime uma mensagem indicando a abertura das portas físicas do `local`.
    * `public boolean verificarLotacao()`: Verifica se a quantidade atual de check-ins atingiu a `capacidadeMaxima`. Retorna `true` se o evento estiver lotado.
* **8. `EventoOnline.java`** (extends Evento)
  * **Atributos:** `String linkAcesso`, `String plataforma`
  * **Métodos Específicos:**
    * `public void iniciarEvento()`: Altera o `status` (herdado) para "Em andamento" e aciona o método `enviarLinkAcesso()`.
    * `public void enviarLinkAcesso()`: Simula o envio de e-mails, imprimindo no console: `"Enviando link [linkAcesso] da plataforma [plataforma]"`.

**Módulo 3: Estrutura**
* **9. `Sessao.java`**
  * **Atributos:** `String id`, `String tema`, `String horarioInicio`, `String palestrante`, `Evento eventoVinculado`
  * **Métodos Específicos:**
    * `public void reagendarSessao(String novoHorario)`: Sobrescreve a variável `horarioInicio` com o `novoHorario` recebido e imprime um aviso de reagendamento.
* **10. `Local.java`**
  * **Atributos:** `String id`, `String nome`, `String endereco`, `boolean possuiAcessibilidade`
  * **Métodos Específicos:**
    * `public boolean verificarDisponibilidadeData(String data)`: Checa internamente se a `data` passada já está ocupada. Se o local estiver livre, retorna `true`.

**Módulo 4: Vínculos e Finanças**
* **11. `Inscricao.java`**
  * **Atributos:** `String id`, `Participante participante`, `Sessao sessao`, `String dataInscricao`, `String status`
  * **Métodos Específicos:**
    * `public void confirmar()`: Altera a variável `status` para "Confirmada". Deve ser acionado pelo Pagamento.
    * `public void cancelar()`: Altera a variável `status` para "Cancelada", liberando a vaga na sessão.
* **12. `Pagamento.java`**
  * **Atributos:** `String id`, `Inscricao inscricao`, `double valor`, `String metodoPagamento`, `boolean pago`
  * **Métodos Específicos:**
    * `public boolean processarPagamento()`: Valida se o `valor` > 0, altera o atributo `pago` para `true` e aciona `confirmar()` da inscrição.
    * `public boolean processarPagamento(String cupomDesconto)`: (Sobrecarga) Verifica se o cupom é válido. Se for, deduz uma porcentagem do `valor` e em seguida aciona a validação padrão de pagamento.

**Módulo 5: Finalização**
* **13. `Categoria.java`**
  * **Atributos:** `String id`, `String nome`, `String descricao`
  * **Métodos Específicos:**
    * `public void atualizarDescricao(String novaDescricao)`: Substitui o valor da variável `descricao` pela `novaDescricao`.
* **14. `Certificado.java`**
  * **Atributos:** `String id`, `Participante participante`, `Evento evento`, `String dataEmissao`, `int cargaHoraria`
  * **Métodos Específicos:**
    * `public void emitir()`: Verifica se o `evento` está "Finalizado" e se o `participante` realizou check-in. Se sim, imprime o diploma formatado no console.
    * `public String gerarCodigoAutenticidade()`: Cria e retorna um hash de validação simples (ex: concatenação do ID do evento com a matrícula do participante).

---

## Parte 4: Regras para os Controllers

Todos os 10 **Controllers** devem possuir a mesma estrutura base para garantir a execução dos CRUDs e a persistência. 

* **Listas em Memória:** Cada Controller deve possuir uma coleção (ex: `List<Participante> listaParticipantes = new ArrayList<>();`). As operações modificam essa lista local.
* **Métodos Obrigatórios em cada Controller:**
  1. `void cadastrar[Entidade]([Entidade] obj)`
  2. `List<[Entidade]> listar[Entidade]s()`
  3. `boolean atualizar[Entidade](String id, [Entidade] objAtualizado)`
  4. `boolean deletar[Entidade](String id)`
  5. `void salvarDadosArquivo()` (Aciona o `ArquivoUtil` para sobrescrever o arquivo .txt/.json com a lista atual).
  6. `void carregarDadosArquivo()` (Aciona o `ArquivoUtil` para preencher a lista ao iniciar o programa).

---

## Parte 5: Estrutura do Menu de Navegação (View CLI)

O arquivo `MenuPrincipal.java` concentra a interface com o usuário em um loop (`do-while`). Ao rodar a classe `Main.java`, o sistema deve invocar os métodos `carregarDadosArquivo()` dos controllers e exibir:

```text
===================================================
      SISTEMA DE GESTÃO DE EVENTOS - MENU
===================================================
1. Gerenciar Pessoas (Organizadores e Participantes)
2. Gerenciar Eventos e Estrutura (Locais, Categorias, Sessões)
3. Financeiro e Inscrições (Inscrições e Pagamentos)
4. Execução do Evento (Check-in, Certificados, Relatórios)
0. Salvar e Sair
===================================================
Escolha uma opção: 
```

### Detalhamento das Ações do Menu
* **1. Pessoas (CRUD 1):** Permite cadastrar, listar, atualizar e excluir `Organizador` e `Participante`.
* **2. Eventos (CRUD 2):** Permite cadastrar Locais e Categorias. Permite criar Eventos (escolhendo Online ou Presencial) e vincular Sessões a eles.
* **3. Financeiro (CRUD 3):** Permite ao Participante escolher uma Sessão e criar uma Inscrição. Permite pagar a Inscrição (com ou sem cupom de desconto) e cancelar a inscrição.
* **4. Execução:** 
  * Aprovar/Iniciar evento via Organizador.
  * Realizar Check-in validando a interface `Checkinavel`.
  * Emitir o Certificado do participante.
  * Gerar o relatório final via `RelatorioGeravel`.
* **0. Sair:** Aciona o método `salvarDadosArquivo()` de todos os 10 controllers em laço para garantir persistência e encerra a execução.

---

## Parte 6: Padrões Avançados de Engenharia (Obrigatórios)

Para garantir a estabilidade do sistema, evitar conflitos de código e manter um histórico profissional, a equipe deve seguir rigorosamente as três diretrizes abaixo.

### 6.1. Tratamento de Erros e Exceções Personalizadas
O sistema não deve encerrar abruptamente devido a entradas inválidas ou quebra de regras de negócio. O uso de `try/catch` é obrigatório.
* Criar o pacote `/src/exceptions/`.
* **Exemplos Obrigatórios (`extends RuntimeException`):**
  * `EntidadeNaoEncontradaException`: ID ou CPF não encontrado nas listas.
  * `EventoLotadoException`: Tentativa de inscrição em evento presencial além da capacidade.
  * `PagamentoPendenteException`: Tentativa de gerar certificado sem pagamento.
  * `EntradaInvalidaException`: Tratamento para o Scanner quando o usuário digita texto em vez de número.

### 6.2. Fluxo de Trabalho no Git e GitHub
Com 5 pessoas desenvolvendo, o código na branch `main` deve ser protegido.
* **Branches:** Cada membro deve criar uma *branch* para sua entidade (ex: `feature/participante-inscricao`). Ninguém comita direto na `main`.
* **Commits Semânticos:** Usar prefixos. Ex: `feat: cria classe Participante` ou `fix: corrige desconto`.
* **Pull Requests (PRs):** Ao terminar o desenvolvimento do seu módulo, abrir um PR. Outro membro da equipe deve revisar antes do *merge*.

### 6.3. Padronização do Arquivo de Log
A gravação de logs será gerenciada pela classe `LogUtil.java`. Para garantir a legibilidade do arquivo `log.txt`, o layout de escrita é padronizado.
* **Layout Obrigatório:** `[DATA/HORA] - [NÍVEL] - [MENSAGEM]`
* **Níveis:**
  * `[INFO]`: Sucessos (ex: novo cadastro, pagamento processado).
  * `[WARN]`: Bloqueios de negócio (ex: check-in negado, evento lotado).
  * `[ERROR]`: Exceções técnicas (ex: erro ao ler .json).
* **Exemplo:** `[10/06/2026 14:30:15] - [INFO] - Novo Participante cadastrado: Joao Silva (Matricula: 202601)`