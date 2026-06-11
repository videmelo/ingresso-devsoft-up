## Arquitetura e Tarefas Iniciais
### 1. Configuração Inicial e Divisão

* **Repositório:** Deve ser criado um repositório no GitHub. O controle de versão é obrigatório e todos os membros da equipe (4 a 5 pessoas) devem realizar commits organizados ao longo do desenvolvimento.

* **Regra dos MVCs:** Para garantir a robustez exigida, o sistema não deve ser simples. A regra estabelecida é que cada desenvolvedor deve implementar **2 MVCs (Model, View e Controller)**. Para um grupo de 4 pessoas, são necessárias 8 entidades completas; para 5, 10 entidades.

**Estrutura de Pastas:** A estrutura base do projeto deve ser criada separando em pacotes: `model`, `view`, `controller` e `util` (para os arquivos de salvamento e utilitários).

### 2. Modelagem das Classes (O Coração do Sistema)

A documentação base fornece diretrizes para a estrutura de um sistema de eventos. A camada de Modelo (Model) pode ser estruturada da seguinte forma para cumprir os requisitos de herança, abstração e polimorfismo:

* **Classe Abstrata e Herança:** Criar uma classe abstrata `Pessoa`. A partir dela, derivam-se as subclasses `Organizador` e `Participante`. Também é possível criar uma classe `Evento` com subclasses `EventoPresencial` e `EventoOnline` para demonstrar mais herança e polimorfismo.

* **Interface:** Implementar uma interface chamada `RelatorioGeravel`. As classes de Evento podem assinar esse contrato para gerar relatórios com estatísticas de participação e engajamento. Outra opção é uma interface `Checkinavel` para o controle de presença.

* **Coleções e Associações:** Um `Evento` poderá conter múltiplas `Sessoes`. Para relacionar os participantes aos eventos, sugere-se usar uma coleção estruturada como `Map<Evento, List<Participante>>`.

### 3. Definindo os 3 CRUDs Mínimos

O sistema exige no mínimo 3 CRUDs (Cadastro, Alteração, Deleção e Listagem) que possuam relacionamento entre si. Para o sistema de eventos e ingressos, pode-se usar:

* **CRUD 1 (Pessoas):** Gerenciamento de `Participantes` e `Organizadores`.

* **CRUD 2 (Eventos e Sessões):** Gerenciamento de eventos, definição de modalidades (online/presencial) e adição de sessões específicas.

* **CRUD 3 (Inscrições/Ingressos):** Onde ocorre a associação. O participante se inscreve em uma sessão ou compra um ingresso, gerando um histórico.

### 4. Persistência de Dados e Logs

* **Arquivos TXT ou JSON:** O armazenamento de dados será feito em arquivos de texto ou JSON. É importante garantir que, em caso de falha do sistema, nenhum dado de ingresso ou cadastro seja perdido.

* **Log do Sistema:** É obrigatório criar um arquivo `.txt` separado apenas para Logs. Sempre que um evento for criado, um check-in for feito ou uma exceção (erro) ocorrer, essa informação deve ser registrada.

## Regras dos MVCs (A Matemática da Equipe)

O documento estabelece que o projeto deve conter 2 MVCs (Model, View e Controller) por desenvolvedor. Para uma equipe de 5 integrantes, **o sistema precisará ter exatamente 10 classes Model (entidades), 10 Controllers e 10 Views**.

Para garantir a complexidade adequada e cumprir os requisitos de herança, abstração e os 3 CRUDs interligados, apresenta-se uma sugestão de 10 entidades lógicas para divisão:

1. **Organizador:** Subclasse que herda da classe abstrata `Pessoa`.
2. **Participante:** Subclasse que também herda de `Pessoa`.
3. **EventoPresencial:** Subclasse que herda da classe abstrata `Evento`.
4. **EventoOnline:** Subclasse que também herda de `Evento`.
5. **Sessao:** As palestras ou atividades específicas dentro de um evento maior.
6. **Inscricao:** Classe de associação que liga o `Participante` à `Sessao`.
7. **Local:** Gerencia a capacidade, endereço e salas dos eventos presenciais.
8. **Categoria:** Define o tipo do evento (ex: Tecnologia, Música, Educação).
9. **Certificado:** Gerado para o participante após a conclusão e check-in.
10. **Pagamento:** Gerencia a transação e o valor total do ingresso do participante.

>**Dica:** Para evitar conflitos de código no controle de versão, cada membro deve ficar responsável pelo desenvolvimento completo (Model, View, Controller e persistência) de entidades específicas. Exemplo: um desenvolvedor assume `Participante` e `Inscricao`.

---

## Estrutura de Pastas (O Esqueleto do Projeto)

Utilizando os princípios de *Clean Code* e a arquitetura MVC, a estrutura de pacotes deve ser modular. O projeto ficará estruturado da seguinte forma:

* **/sistema_eventos**
* **/model:** Entidades principais, além das classes abstratas base (`Pessoa` e `Evento`).
* **/interfaces:** Contratos, como as interfaces `RelatorioGeravel` ou `Autenticavel`.
* **/controller:** Controladores (ex: `ParticipanteController`, `SessaoController`), contendo a lógica de negócio, laços e estruturas de controle.
* **/view:** Classes de interface de usuário (menus no console, leitura de dados).
* **/util:** Classes utilitárias, como `ArquivoUtil` para manipular arquivos (.txt/.json) e a classe responsável por gerar os logs do sistema.
* **Main.java:** O ponto de partida que inicializa o sistema e chama o menu principal.

Ter a visão global da árvore do projeto antes de iniciar o código é a melhor forma de evitar refatorações e garantir o alinhamento da equipe.

Abaixo está a árvore completa do Sistema de Gestão de Eventos, dividida em duas visões: a **Árvore de Hierarquia (Foco nas Superclasses e Herança)** e a **Árvore de Pastas (Foco na Arquitetura MVC)**.

### 1. Árvore de Hierarquia (O mapa das Superclasses)

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

---

### 2. Árvore de Arquitetura de Pastas (O mapa físico do projeto)

Esta é a estrutura recomendada para organização das entidades e aplicação dos princípios de *Clean Code*:

```text
sistema_eventos/
│
├── src/
│   ├── model/                  # Camada de Dados
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

Com essa árvore estruturada, o ambiente está preparado para o início da implementação.

## Modelagem das Classes, Atributos, Métodos e Contratos

Abaixo está o detalhamento de como cada arquivo da camada de Modelo (`/src/model/` e `/src/interfaces/`) deve ser implementado. Cada item abaixo representa **um arquivo .java independente**.

> **Regras Gerais Obrigatórias para as Classes de Modelo:**
> 1. **Encapsulamento:** Todos os atributos devem ser definidos como `private`.
> 2. **Acesso:** Para cada atributo, deve ser criado um método `public [Tipo] getNomeDoAtributo()` e um `public void setNomeDoAtributo([Tipo] parametro)`.
> 3. **Construtores:** Toda classe concreta deve ter pelo menos um construtor público que inicializa seus atributos.
> 4. **Exibição:** Toda classe deve sobrescrever o método `public String toString()` para retornar uma representação em texto dos dados da classe.

---

### Contratos e Superclasses (Arquivos Base do Sistema)

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

---

### Modelagem por Entidade Concreta (Arquivos dos Modelos)

#### **Módulo 1: Pessoas e Herança**

#### 5. Arquivo: `Organizador.java`
* **Local:** `/src/model/Organizador.java`
* **Herança:** `public class Organizador extends Pessoa`
* **Atributos Específicos (`private`):** `String setor`, `String nivelAcesso`
* **Métodos Obrigatórios:**
  * Construtor invocando o `super(id, nome, cpf, email)` mais os atributos específicos.
  * Getters e Setters para `setor` e `nivelAcesso`.
  * `public void exibirDadosPessoais()`
    * **O que deve fazer:** Imprime no console uma ficha formatada: `[ORGANIZADOR] Nome: X | CPF: Y | Setor: Z | Nível: W`.
  * `public boolean aprovarEvento(Evento evento)`
    * **O que deve fazer:** Verifica se o `nivelAcesso` do organizador permite aprovação (ex: nível "Admin"). Se sim, altera o status do objeto `evento` passado como parâmetro para "Aprovado" e retorna `true`.
  * `public String toString()` sobrescrito.

#### 6. Arquivo: `Participante.java`
* **Local:** `/src/model/Participante.java`
* **Herança/Interface:** `public class Participante extends Pessoa implements Checkinavel`
* **Atributos Específicos (`private`):** `String matricula`, `List<Inscricao> historicoInscricoes`
* **Métodos Obrigatórios:**
  * Construtor invocando o `super(...)` mais a inicialização da lista de inscrições.
  * Getters e Setters para `matricula` e `historicoInscricoes`.
  * `public void exibirDadosPessoais()`
    * **O que deve fazer:** Imprime no console: `[PARTICIPANTE] Nome: X | Matrícula: Y | Total Inscrições: Z`.
  * `public boolean realizarCheckin()`
    * **O que deve fazer:** (Implementação da interface). Verifica se a lista `historicoInscricoes` possui alguma inscrição confirmada. Se possuir, valida o check-in e retorna `true`.
  * `public String toString()` sobrescrito.

#### **Módulo 2: Eventos Físicos e Digitais**

#### 7. Arquivo: `EventoPresencial.java`
* **Local:** `/src/model/EventoPresencial.java`
* **Herança:** `public class EventoPresencial extends Evento`
* **Atributos Específicos (`private`):** `Local local`, `int capacidadeMaxima`
* **Métodos Obrigatórios:**
  * Construtor invocando `super(...)` mais atributos específicos.
  * Getters e Setters.
  * `public void iniciarEvento()`
    * **O que deve fazer:** Altera o `status` (herdado) para "Em andamento" e imprime uma mensagem indicando a abertura das portas do `local` físico.
  * `public boolean verificarLotacao()`
    * **O que deve fazer:** Verifica se a quantidade atual de inscrições (ou check-ins) atingiu a `capacidadeMaxima`. Retorna `true` se o evento estiver lotado.
  * `public String toString()` sobrescrito.

#### 8. Arquivo: `EventoOnline.java`
* **Local:** `/src/model/EventoOnline.java`
* **Herança:** `public class EventoOnline extends Evento`
* **Atributos Específicos (`private`):** `String linkAcesso`, `String plataforma` (ex: Zoom, Teams)
* **Métodos Obrigatórios:**
  * Construtor invocando `super(...)` mais atributos específicos.
  * Getters e Setters.
  * `public void iniciarEvento()`
    * **O que deve fazer:** Altera o `status` (herdado) para "Em andamento" e chama o método `enviarLinkAcesso()`.
  * `public void enviarLinkAcesso()`
    * **O que deve fazer:** Simula o envio de e-mails, imprimindo no console: `"Enviando link [linkAcesso] da plataforma [plataforma] para os participantes"`.
  * `public String toString()` sobrescrito.

#### **Módulo 3: Logística e Estrutura**

#### 9. Arquivo: `Sessao.java`
* **Local:** `/src/model/Sessao.java`
* **Atributos (`private`):** `String id`, `String tema`, `String horarioInicio`, `String palestrante`, `Evento eventoVinculado`
* **Métodos Obrigatórios:**
  * Construtor completo.
  * Getters e Setters.
  * `public void reagendarSessao(String novoHorario)`
    * **O que deve fazer:** Sobrescreve a variável `horarioInicio` com o `novoHorario` recebido. Adicionalmente, pode imprimir no console um aviso do reagendamento.
  * `public String toString()` sobrescrito.

#### 10. Arquivo: `Local.java`
* **Local:** `/src/model/Local.java`
* **Atributos (`private`):** `String id`, `String nome`, `String endereco`, `boolean possuiAcessibilidade`
* **Métodos Obrigatórios:**
  * Construtor completo.
  * Getters e Setters.
  * `public boolean verificarDisponibilidadeData(String data)`
    * **O que deve fazer:** (Neste MVP simples): Pode checar internamente se a `data` passada já está ocupada no escopo desse local. Se o local estiver livre, retorna `true`.
  * `public String toString()` sobrescrito.

#### **Módulo 4: Vínculos e Finanças**

#### 11. Arquivo: `Inscricao.java`
* **Local:** `/src/model/Inscricao.java`
* **Atributos (`private`):** `String id`, `Participante participante`, `Sessao sessao`, `String dataInscricao`, `String status` (ex: Pendente, Confirmada, Cancelada)
* **Métodos Obrigatórios:**
  * Construtor completo.
  * Getters e Setters.
  * `public void confirmar()`
    * **O que deve fazer:** Altera a variável `status` para "Confirmada". Pode ser chamado pelo Pagamento após este ser processado.
  * `public void cancelar()`
    * **O que deve fazer:** Altera a variável `status` para "Cancelada". Libera a vaga na sessão.
  * `public String toString()` sobrescrito.

#### 12. Arquivo: `Pagamento.java`
* **Local:** `/src/model/Pagamento.java`
* **Atributos (`private`):** `String id`, `Inscricao inscricao`, `double valor`, `String metodoPagamento`, `boolean pago`
* **Métodos Obrigatórios:**
  * Construtor completo.
  * Getters e Setters.
  * `public boolean processarPagamento()`
    * **O que deve fazer:** Valida se o valor é maior que zero. Altera o atributo `pago` para `true` e chama o método `confirmar()` do objeto `inscricao`. Retorna `true` se tudo ocorrer bem.
  * `public boolean processarPagamento(String cupomDesconto)`
    * **O que deve fazer:** (Polimorfismo de sobrecarga). Verifica se o `cupomDesconto` é válido (ex: "DESC10"). Se for, reduz 10% da variável `valor`, imprime a aplicação do desconto no console e chama `processarPagamento()`.
  * `public String toString()` sobrescrito.

#### **Módulo 5: Classificação e Finalização**

#### 13. Arquivo: `Categoria.java`
* **Local:** `/src/model/Categoria.java`
* **Atributos (`private`):** `String id`, `String nome` (ex: TI, Design, Negócios), `String descricao`
* **Métodos Obrigatórios:**
  * Construtor completo.
  * Getters e Setters.
  * `public void atualizarDescricao(String novaDescricao)`
    * **O que deve fazer:** Simplesmente substitui o valor da variável `descricao` pela `novaDescricao`.
  * `public String toString()` sobrescrito.

#### 14. Arquivo: `Certificado.java`
* **Local:** `/src/model/Certificado.java`
* **Atributos (`private`):** `String id`, `Participante participante`, `Evento evento`, `String dataEmissao`, `int cargaHoraria`
* **Métodos Obrigatórios:**
  * Construtor completo.
  * Getters e Setters.
  * `public void emitir()`
    * **O que deve fazer:** Verifica se o evento associado já está com o status "Finalizado" e se o participante realizou check-in. Se sim, imprime no console o diploma formatado (Nome do Participante, Carga Horária, Evento).
  * `public String gerarCodigoAutenticidade()`
    * **O que deve fazer:** Cria e retorna um hash simples para validação. Pode ser a concatenação do `id` do evento com o `cpf` ou `matricula` do participante. Retorna a String gerada.
  * `public String toString()` sobrescrito.

---

### Regras para os Controllers

Todos os 10 **Controllers** devem possuir a mesma estrutura base para garantir a execução dos 3 CRUDs e a persistência em arquivo. Deve ser implementada a seguinte lógica de métodos em cada Controller:

1. `void cadastrar[Entidade]([Entidade] obj)`
2. `List<[Entidade]> listar[Entidade]s()`
3. `boolean atualizar[Entidade](String id, [Entidade] objAtualizado)`
4. `boolean deletar[Entidade](String id)`
5. `void salvarDadosArquivo()` *(Chamada à classe `ArquivoUtil` para salvar a lista em .txt ou .json)*
6. `void carregarDadosArquivo()` *(Chamada à classe `ArquivoUtil` para preencher as coleções durante a inicialização do sistema)*

*Observação:* Para manter os dados em memória durante a execução, cada Controller deve possuir uma **Coleção** (ex: `List<Participante> listaParticipantes = new ArrayList<>();`). As operações de CRUD modificam essa lista e, ao final, a coleção completa deve ser salva no arquivo.