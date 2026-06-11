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

Abaixo está o detalhamento completo da modelagem das classes, estruturado como um dicionário de dados para orientar a implementação dos Modelos (Models), garantindo o cumprimento dos requisitos de Orientação a Objetos (Herança, Polimorfismo, Interfaces e Encapsulamento).

> **Regra Geral para todas as classes:** Todos os atributos devem ser **privados** (encapsulamento), acessados por meio de métodos `get` e `set`. Todas as classes precisam de um construtor e de um método `toString()` sobrescrito para facilitar a exibição.

---

### Contratos e Superclasses (Base do Sistema)

**1. Interface `Checkinavel`**

* **Método:** `boolean realizarCheckin()` - Contrato para validar a presença do usuário.

**2. Interface `RelatorioGeravel`**

* **Método:** `void gerarRelatorio()` - Contrato para exportar dados de engajamento do evento.

**3. Classe Abstrata `Pessoa`**

* **Atributos:** `String id`, `String nome`, `String cpf`, `String email`
* **Métodos:** `abstract void exibirDadosPessoais()` (obriga as subclasses a implementarem).

**4. Classe Abstrata `Evento` (implementa `RelatorioGeravel`)**

* **Atributos:** `String id`, `String titulo`, `String data`, `String status` (ex: Agendado, Em andamento, Finalizado)
* **Métodos:** `abstract void iniciarEvento()`, `void gerarRelatorio()` (implementação da interface).

---

### Modelagem por Entidade

#### **Módulo 1 (Foco: Pessoas e Herança)**

**5. `Organizador` (extends `Pessoa`)**

* **Atributos Específicos:** `String setor`, `String nivelAcesso`
* **Métodos Específicos:**
* `void exibirDadosPessoais()` *(Polimorfismo de sobrescrita: exibe os dados no formato de organizador).*
* `boolean aprovarEvento(Evento evento)`

**6. `Participante` (extends `Pessoa` implements `Checkinavel`)**

* **Atributos Específicos:** `String matricula`, `List<Inscricao> historicoInscricoes`
* **Métodos Específicos:**
* `void exibirDadosPessoais()` *(Polimorfismo de sobrescrita: exibe os dados no formato de participante).*
* `boolean realizarCheckin()` *(Implementação da interface).*

#### **Módulo 2 (Foco: Eventos e Herança)**

**7. `EventoPresencial` (extends `Evento`)**

* **Atributos Específicos:** `Local local`, `int capacidadeMaxima`
* **Métodos Específicos:**
* `void iniciarEvento()` *(Polimorfismo de sobrescrita: lógica para evento físico).*
* `boolean verificarLotacao()`

**8. `EventoOnline` (extends `Evento`)**

* **Atributos Específicos:** `String linkAcesso`, `String plataforma` (ex: Zoom, Teams)
* **Métodos Específicos:**
* `void iniciarEvento()` *(Polimorfismo de sobrescrita: lógica para liberar o link).*
* `void enviarLinkAcesso()`

#### **Módulo 3 (Foco: Estrutura e Logística)**

**9. `Sessao`**

* **Atributos Específicos:** `String id`, `String tema`, `String horarioInicio`, `String palestrante`, `Evento eventoVinculado`
* **Métodos Específicos:** `void reagendarSessao(String novoHorario)`

**10. `Local`**

* **Atributos Específicos:** `String id`, `String nome`, `String endereco`, `boolean possuiAcessibilidade`
* **Métodos Específicos:** `boolean verificarDisponibilidadeData(String data)`

#### **Módulo 4 (Foco: Associação e Financeiro)**

**11. `Inscricao`**

* **Atributos Específicos:** `String id`, `Participante participante`, `Sessao sessao`, `String dataInscricao`, `String status` (ex: Pendente, Confirmada, Cancelada)
* **Métodos Específicos:** `void confirmar()`, `void cancelar()`

**12. `Pagamento`**

* **Atributos Específicos:** `String id`, `Inscricao inscricao`, `double valor`, `String metodoPagamento`, `boolean pago`
* **Métodos Específicos:**
* `boolean processarPagamento()`
* `boolean processarPagamento(String cupomDesconto)` *(Polimorfismo de sobrecarga: mesmo método, parâmetros diferentes).*

#### **Módulo 5 (Foco: Classificação e Finalização)**

**13. `Categoria`**

* **Atributos Específicos:** `String id`, `String nome` (ex: TI, Design, Negócios), `String descricao`
* **Métodos Específicos:** `void atualizarDescricao(String novaDescricao)`

**14. `Certificado`**

* **Atributos Específicos:** `String id`, `Participante participante`, `Evento evento`, `String dataEmissao`, `int cargaHoraria`
* **Métodos Específicos:** `void emitir()`, `String gerarCodigoAutenticidade()`

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