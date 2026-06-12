# Documento de Decisão de Arquitetura (ADR)
**Projeto:** Sistema de Gestão de Eventos

## Descrição e Objetivo do Projeto

O **Sistema de Gestão de Eventos** é uma aplicação de linha de comando (CLI) desenvolvida em Java, criada para gerenciar de ponta a ponta o ciclo de vida de eventos acadêmicos, corporativos ou de entretenimento. 

* **O que o sistema faz?**
  Ele permite que organizadores estruturem eventos (físicos ou online), definam categorias, aloquem locais e criem cronogramas de sessões (palestras/atividades). Para os usuários, permite que participantes se cadastrem, realizem inscrições em sessões, processem pagamentos (com suporte a cupons de desconto), façam check-in de presença e, ao final, emitam certificados validados por um código de autenticidade.

* **Qual o propósito técnico?**
  Além de sua função de negócio, o projeto tem o objetivo acadêmico/técnico de demonstrar a aplicação rigorosa de **Programação Orientada a Objetos (POO)** — englobando herança, polimorfismo, interfaces e classes abstratas —, arquitetura estruturada em **MVC (Model-View-Controller)** e **persistência de dados local** utilizando arquivos de texto (`.txt` ou `.json`), sem o uso de banco de dados comercial.

---

Este documento define as diretrizes arquiteturais, a divisão de tarefas, a modelagem de dados e os padrões de engenharia que devem ser seguidos por toda a equipe (5 desenvolvedores) durante a construção do sistema.

## Parte 1: Visão Geral e Regras de Negócio

### 1.1. Configuração Inicial e Divisão da Equipe
* **Repositório:** Deve ser criado um repositório no GitHub. O controle de versão é obrigatório e todos os membros da equipe (5 pessoas) devem realizar commits organizados ao longo do desenvolvimento.
* **A Regra dos MVCs:** Para garantir a robustez exigida, o sistema não deve ser simples. A regra estabelecida é que **cada desenvolvedor deve implementar 2 MVCs (Model, View e Controller)**. Como a equipe possui 5 integrantes, o sistema precisará ter exatamente 10 classes Model (entidades), 10 Controllers e 10 Views.
  * **Dica:** Para evitar conflitos de código no controle de versão, cada membro deve ficar responsável pelo desenvolvimento completo (Model, View, Controller e persistência) de entidades específicas. Exemplo: um desenvolvedor assume `Participante` e `Inscricao`.

### 1.2. Os 3 CRUDs Mínimos Interligados
O sistema exige no mínimo 3 CRUDs (Cadastro, Alteração, Deleção e Listagem) que possuam relacionamento entre si. Para o sistema de eventos e ingressos, pode-se usar:
* **CRUD 1 (Pessoas):** Gerenciamento de `Participantes` e `Organizadores`.
* **CRUD 2 (Eventos e Sessões):** Gerenciamento de eventos, definição de modalidades (online/presencial) e adição de sessões específicas.
* **CRUD 3 (Inscrições/Ingressos):** Onde ocorre a associação. O participante se inscreve em uma sessão ou compra um ingresso, gerando um histórico.

### 1.3. Persistência de Dados e Logs
* **Arquivos TXT ou JSON:** O armazenamento de dados será feito em arquivos de texto ou JSON. É importante garantir que, em caso de falha do sistema, nenhum dado de ingresso ou cadastro seja perdido.
* **Log do Sistema:** É obrigatório criar um arquivo `.txt` separado apenas para Logs. Sempre que um evento for criado, um check-in for feito ou uma exceção (erro) ocorrer, essa informação deve ser registrada.

## Parte 2: Estrutura do Projeto (Arquitetura)

Utilizando os princípios de *Clean Code* e a arquitetura MVC, a estrutura de pacotes deve ser modular.

### 2.1. Árvore de Arquitetura de Pastas (O mapa físico)
Esta é a estrutura recomendada para organização das entidades e aplicação dos princípios de *Clean Code*:

```text
sistema_eventos/
│
├── src/
│   ├── model/                  # Camada de Dados (Entidades, Abstratas e Enums)
│   │   ├── Pessoa.java         # (Superclasse Abstrata)
│   │   ├── Organizador.java
│   │   ├── Participante.java
│   │   ├── Evento.java         # (Superclasse Abstrata)
│   │   ├── EventoPresencial.java
│   │   ├── EventoOnline.java
│   │   ├── Sessao.java
│   │   ├── Inscricao.java
│   │   ├── Local.java
│   │   ├── Categoria.java
│   │   ├── Pagamento.java
│   │   └── Certificado.java
│   │
│   ├── interfaces/             # Contratos
│   │   ├── RelatorioGeravel.java
│   │   └── Checkinavel.java
│   │
│   ├── controller/             # Lógica e Controle
│   │   ├── OrganizadorController.java
│   │   ├── ParticipanteController.java
│   │   ├── EventoPresencialController.java
│   │   ├── EventoOnlineController.java
│   │   ├── SessaoController.java
│   │   ├── InscricaoController.java
│   │   ├── LocalController.java
│   │   ├── CategoriaController.java
│   │   ├── PagamentoController.java
│   │   └── CertificadoController.java
│   │
│   ├── view/                   # Telas e Menus no Console
│   │   ├── MenuPrincipal.java  # Tela inicial unificada
│   │   ├── OrganizadorView.java
│   │   ├── ParticipanteView.java
│   │   └── ... (demais Views correspondentes)
│   │
│   ├── util/                   # Ferramentas Gerais
│   │   ├── ArquivoUtil.java    # Gravação/Leitura em .txt ou .json
│   │   └── LogUtil.java        # Gera o log obrigatório de erros/atividades
│   │
│   └── Main.java               # Classe principal que roda o sistema
│
├── dados/                      # Diretório para arquivos .txt ou .json
├── logs/                       # Diretório para arquivos de log
├── README.md                   # Documentação obrigatória do projeto
└── .gitignore                  # Para ignorar arquivos de compilação
```

Ter a visão global da árvore do projeto antes de iniciar o código é a melhor forma de evitar refatorações e garantir o alinhamento da equipe.

### 2.2. Árvore de Hierarquia (O mapa das Superclasses)
Esta árvore mostra como as classes se relacionam através de herança (Superclasses e Subclasses) e quais implementarão as interfaces, atendendo diretamente aos requisitos:

```text
├── [Superclasse Abstrata] Pessoa
│   ├── [Subclasse] Organizador
│   └── [Subclasse] Participante (Implementa a interface Checkinavel)
│
├── [Superclasse Abstrata] Evento (Implementa a interface RelatorioGeravel)
│   ├── [Subclasse] EventoPresencial
│   └── [Subclasse] EventoOnline
│
├── [Entidades Independentes / Relacionais]
│   ├── Sessao (Vinculada a um Evento)
│   ├── Local (Vinculado a um EventoPresencial)
│   ├── Categoria (Vinculada a um Evento)
│   ├── Inscricao (Associa um Participante a uma Sessao)
│   ├── Pagamento (Vinculado a uma Inscricao)
│   └── Certificado (Gerado para um Participante após o check-in)
│
└── [Interfaces]
    ├── RelatorioGeravel (Gera dados de engajamento/frequência)
    └── Checkinavel (Permite registrar presença)
```

## Parte 3: Dicionário de Dados e Modelagem de Classes

Abaixo está o detalhamento de como cada arquivo da camada de Modelo (`/src/model/` e `/src/interfaces/`) deve ser implementado. Cada item abaixo representa **um arquivo .java independente**.

A documentação base fornece diretrizes para a estrutura de um sistema de eventos. A camada de Modelo (Model) pode ser estruturada da seguinte forma para cumprir os requisitos de herança, abstração e polimorfismo:
* **Classe Abstrata e Herança:** Criar uma classe abstrata `Pessoa`. A partir dela, derivam-se as subclasses `Organizador` e `Participante`. Também é possível criar uma classe `Evento` com subclasses `EventoPresencial` e `EventoOnline` para demonstrar mais herança e polimorfismo.
* **Interface:** Implementar uma interface chamada `RelatorioGeravel`. As classes de Evento podem assinar esse contrato para gerar relatórios com estatísticas de participação e engajamento. Outra opção é uma interface `Checkinavel` para o controle de presença.
* **Coleções e Associações:** Um `Evento` poderá conter múltiplas `Sessoes`. Para relacionar os participantes aos eventos, sugere-se usar uma coleção estruturada como `Map<Evento, List<Participante>>`.

> **Regras Gerais Obrigatórias para as Classes de Modelo:**
> 1. **Encapsulamento:** Todos os atributos devem ser definidos como `private`.
> 2. **Acesso:** Para cada atributo, deve ser criado um método `public [Tipo] getNomeDoAtributo()` e um `public void setNomeDoAtributo([Tipo] parametro)`.
> 3. **Construtores:** Toda classe concreta deve ter pelo menos um construtor público que inicializa seus atributos.
> 4. **Exibição:** Toda classe deve sobrescrever o método `public String toString()` para retornar uma representação em texto dos dados da classe.

### 3.1. Contratos e Superclasses (Arquivos Base do Sistema)

#### 1. Arquivo: `Checkinavel.java` (Interface)
* **Local:** `/src/interfaces/Checkinavel.java`
* **Propósito:** Contrato para entidades que necessitam de validação de presença.
* **Conteúdo Obrigatório:**
  * `public boolean realizarCheckin();`
    * **O que deve fazer:** Deve verificar se o check-in é válido (ex: se a data atual corresponde à data do evento ou se o participante possui inscrição confirmada). Retorna `true` se o check-in for bem-sucedido e altera o status interno de presença, e `false` caso contrário.

#### 2. Arquivo: `RelatorioGeravel.java` (Interface)
* **Local:** `/src/interfaces/RelatorioGeravel.java`
* **Propósito:** Contrato para entidades que podem gerar dados estatísticos ou de engajamento.
* **Conteúdo Obrigatório:**
  * `public void gerarRelatorio();`
    * **O que deve fazer:** Deve realizar a leitura dos dados consolidados da entidade (como total de inscritos, total de check-ins, receitas geradas) e formatar um texto para ser impresso no console ou salvo em um arquivo txt.

#### 3. Arquivo: `Pessoa.java` (Classe Abstrata)
* **Local:** `/src/model/Pessoa.java`
* **Propósito:** Classe base que agrupa atributos comuns a todas as pessoas do sistema. Não deve ser instanciada diretamente.
* **Atributos (`private`):** `String id`, `String nome`, `String cpf`, `String email`
* **Métodos Obrigatórios:**
  * Construtor padrão e/ou construtor recebendo os atributos.
  * Getters e Setters para todos os atributos.
  * `public abstract void exibirDadosPessoais();`
    * **O que deve fazer:** Por ser abstrato, não possui corpo aqui. Obriga que `Organizador` e `Participante` implementem como seus dados devem ser exibidos no console (usando `System.out.println`).

#### 4. Arquivo: `Evento.java` (Classe Abstrata)
* **Local:** `/src/model/Evento.java`
* **Propósito:** Classe base para os tipos de eventos.
* **Implementa:** `RelatorioGeravel` (`public abstract class Evento implements RelatorioGeravel`)
* **Atributos (`private`):** `String id`, `String titulo`, `String data`, `String status` (ex: "Agendado", "Em andamento", "Finalizado")
* **Métodos Obrigatórios:**
  * Construtor recebendo os atributos.
  * Getters e Setters para todos os atributos.
  * `public abstract void iniciarEvento();`
    * **O que deve fazer:** Por ser abstrato, obriga as filhas a definirem o que significa "iniciar".
  * `public void gerarRelatorio()`
    * **O que deve fazer:** Implementa a interface. Deve exibir no console um resumo contendo o `titulo`, a `data` e o `status` atual do evento de forma amigável.

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
    * `public boolean verificarLotacao() throws EventoLotadoException`: Verifica se a quantidade atual de inscrições/check-ins atingiu a `capacidadeMaxima`. Se estiver lotado, lança a exceção `EventoLotadoException`, impedindo novas inscrições. Retorna `false` caso ainda haja vagas.
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
    * `public void emitir() throws PagamentoPendenteException`: Verifica se o `evento` está "Finalizado" e se o `participante` realizou check-in. Também deve validar se a inscrição foi paga; se não, lança `PagamentoPendenteException`. Se tudo estiver válido, imprime o diploma formatado no console.
    * `public String gerarCodigoAutenticidade()`: Cria e retorna um hash de validação simples (ex: concatenação do ID do evento com a matrícula do participante).

## Parte 4: Regras para os Controllers

Todos os 10 **Controllers** devem possuir a mesma estrutura base para garantir a execução dos 3 CRUDs e a persistência em arquivo. Deve ser implementada a seguinte lógica de métodos em cada Controller:

1. `void cadastrar[Entidade]([Entidade] obj)`
2. `List<[Entidade]> listar[Entidade]s()`
3. `boolean atualizar[Entidade](String id, [Entidade] objAtualizado)`
4. `boolean deletar[Entidade](String id)`
5. `void salvarDadosArquivo()` *(Chamada à classe `ArquivoUtil` para salvar a lista em .txt ou .json)*
6. `void carregarDadosArquivo()` *(Chamada à classe `ArquivoUtil` para preencher as coleções durante a inicialização do sistema)*

*Observação:* Para manter os dados em memória durante a execução, cada Controller deve possuir uma **Coleção** (ex: `List<Participante> listaParticipantes = new ArrayList<>();`). As operações de CRUD modificam essa lista e, ao final, a coleção completa deve ser salva no arquivo.

## Parte 5: Estrutura do Menu de Navegação (View CLI)

Para garantir uma navegação fluida e lógica no terminal, o arquivo `MenuPrincipal.java` (na camada View) deve concentrar a interface primária com o usuário. Ao rodar a classe `Main.java`, o sistema deve carregar os dados dos arquivos (`carregarDadosArquivo()`) e exibir um menu iterativo em loop (`do-while` ou `while(true)`).

Abaixo está o mapeamento de como o menu deve ser apresentado no console e quais ações cada opção deve desencadear nos Controllers.

### Menu Principal
Ao iniciar, o sistema exibe o seguinte layout:

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

### Detalhamento dos Submenus e Ações

#### **Opção 1: Gerenciar Pessoas**
Neste submenu, o foco é o cumprimento do **CRUD 1 (Pessoas)**.
* **1.1. Cadastrar Novo Organizador:** Pede dados via `Scanner` e envia para `OrganizadorController.cadastrar()`.
* **1.2. Cadastrar Novo Participante:** Pede dados (nome, CPF, matrícula) e envia para `ParticipanteController.cadastrar()`.
* **1.3. Listar Todos os Usuários:** Chama a listagem de ambas as entidades.
* **1.4. Atualizar Dados de Usuário:** Pede o ID/CPF, busca a entidade correspondente e permite editar as informações.
* **1.5. Excluir Usuário:** Remove o usuário das listas em memória.

#### **Opção 2: Gerenciar Eventos e Estrutura**
Neste submenu, o foco é o **CRUD 2 (Eventos e Sessões)**. 
* **2.1. Cadastrar Categoria:** Define um tema (ex: TI, Design) via `CategoriaController`.
* **2.2. Cadastrar Local (Físico):** Registra o espaço físico (nome, endereço, acessibilidade).
* **2.3. Criar Evento:** O sistema pergunta se é "Presencial" ou "Online". Coleta os dados, vincula à Categoria (e ao Local, se físico), e cadastra com status "Agendado".
* **2.4. Adicionar Sessão a um Evento:** Cria uma palestra/atividade (`Sessao`) e a vincula a um Evento existente.
* **2.5. Listar Eventos Disponíveis:** Exibe todos os eventos cadastrados.

#### **Opção 3: Financeiro e Inscrições**
Neste submenu, o foco é o **CRUD 3 (Inscrições/Ingressos)**, fazendo o elo entre Participantes e Eventos/Sessões.
* **3.1. Realizar Inscrição:** O sistema lista os Participantes e as Sessões. O usuário escolhe os respectivos IDs. O `InscricaoController` cria o registro com status "Pendente".
* **3.2. Pagar Inscrição / Aplicar Desconto:** O sistema lista as inscrições "Pendentes". O usuário escolhe uma e aciona o `PagamentoController`. O sistema deve perguntar se existe um cupom de desconto (para validar o *Polimorfismo de sobrecarga*). Ao finalizar, muda o status da inscrição para "Confirmada".
* **3.3. Cancelar Inscrição:** Busca pelo ID da inscrição e altera seu status para "Cancelada", liberando a vaga.

#### **Opção 4: Execução do Evento**
Opções utilizadas durante ou após a ocorrência do evento, acionando interfaces e regras de negócio avançadas.
* **4.1. Aprovar e Iniciar Evento:** Pede o ID do evento. Chama a lógica do Organizador para "aprovar" e em seguida chama o método abstrato `iniciarEvento()` (que vai se comportar diferente se for Online ou Presencial), mudando o status para "Em andamento".
* **4.2. Realizar Check-in:** Pede a matrícula do participante. Aciona o contrato da interface `Checkinavel` para validar a presença na sessão inscrita.
* **4.3. Gerar Certificado:** Pede a matrícula do participante. Valida se o check-in foi realizado e se o evento consta como "Finalizado". Se sim, imprime o diploma na tela e gera o código de autenticidade.
* **4.4. Gerar Relatório de Engajamento:** Chama o contrato `gerarRelatorio()` da interface `RelatorioGeravel` do Evento, imprimindo no console um balanço final (ex: total de inscritos, total de comparecimentos).

#### **Opção 0: Salvar e Sair**
* **Ação:** O sistema deve executar um laço (ou chamadas explícitas) nos 10 Controllers, acionando o método `salvarDadosArquivo()`. Isso garante que todo o estado em memória (listas atualizadas, pagamentos feitos, novos status) seja gravado de forma segura nos arquivos `.txt` ou `.json`. Em seguida, encerra a aplicação de forma limpa.

## Parte 6: Padrões Avançados de Engenharia (Obrigatórios para a Equipe)

Para garantir a estabilidade do sistema, evitar conflitos de código e manter um histórico profissional, a equipe deve seguir rigorosamente as três diretrizes abaixo.

### 6.1. Tratamento de Erros e Exceções Personalizadas
O sistema não deve encerrar abruptamente ("crachar") devido a entradas inválidas do usuário ou regras de negócio violadas. O uso de blocos `try/catch` é obrigatório, aliado à criação de exceções próprias.

* **Pacote de Exceções:** Deve ser criado um pacote `/src/exceptions/`.
* **Classes de Exceção Sugeridas (que herdam de `Exception` ou `RuntimeException`):**
  * `EntidadeNaoEncontradaException`: Disparada quando o usuário digita um ID ou CPF que não existe nas listas.
  * `EventoLotadoException`: Disparada pelo Controller ao tentar inscrever alguém em um `EventoPresencial` cuja `capacidadeMaxima` já foi atingida.
  * `PagamentoPendenteException`: Disparada ao tentar gerar um `Certificado` para uma inscrição que ainda não foi paga.
  * `EntradaInvalidaException`: Disparada nas *Views* se o usuário digitar uma letra quando o menu espera um número (tratamento do `InputMismatchException` do `Scanner`).

### 6.2. Fluxo de Trabalho no Git e GitHub
Com 5 pessoas desenvolvendo simultaneamente, realizar commits diretamente na branch `main` causará conflitos destrutivos. 

* **Regra de Branches:** Ninguém deve comitar na `main`. Cada membro deve criar uma *branch* específica para a entidade que está desenvolvendo. 
  * Exemplo: `git checkout -b feature/participante-inscricao`.
* **Commits Semânticos:** As mensagens de commit devem deixar claro o que foi feito, utilizando prefixos padrões:
  * `feat: cria classe Participante e metodos base` (Para novas funcionalidades)
  * `fix: corrige bug no calculo de desconto do pagamento` (Para correções)
  * `docs: atualiza o README com instruções de uso` (Para documentação)
* **Pull Requests (PRs):** Ao terminar suas 2 entidades (Model, Controller e View), o membro deve abrir um Pull Request para a `main`. Pelo menos um outro membro do grupo deve revisar o código antes de realizar o *merge*.

### 6.3. Padronização do Arquivo de Log
O documento exige a gravação de logs (atividades e erros). Para que o arquivo `log.txt` não se torne ilegível com cada membro escrevendo de um formato diferente, a classe `LogUtil.java` deve padronizar a saída.

* **Layout Obrigatório da Linha de Log:**
  `[DATA/HORA] - [NÍVEL] - [MENSAGEM]`
* **Níveis de Log Permitidos:**
  * `[INFO]`: Para ações de sucesso (ex: cadastros, aprovação de eventos).
  * `[WARN]`: Para ações de negócio bloqueadas (ex: tentativa de check-in inválida, evento lotado).
  * `[ERROR]`: Para exceções técnicas e falhas de sistema (ex: falha ao ler o arquivo `.dat`, variável nula).
* **Exemplos práticos no arquivo:**
  * `[10/06/2026 14:30:15] - [INFO] - Novo Participante cadastrado: Joao Silva (Matricula: 202601)`
  * `[10/06/2026 15:45:00] - [WARN] - Tentativa de inscricao falhou: Evento ID 05 lotado.`
  * `[10/06/2026 16:00:22] - [ERROR] - Falha ao carregar dadosArquivo(): Arquivo participantes.dat nao encontrado.`