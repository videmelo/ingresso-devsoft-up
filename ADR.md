## Arquitetura e Tarefas Iniciais
### 1. Configuração Inicial e Divisão

* **Repositório:** Criem um repositório no GitHub imediatamente. O controle de versão é obrigatório e **todos** os membros da equipe (4 a 5 alunos) precisam fazer commits organizados ao longo do mês.


* **Regra dos MVCs:** Para garantir a robustez exigida, o sistema não pode ser simples. A regra de ouro é que cada pessoa do grupo deve desenvolver **2 MVCs (Model, View e Controller)**. Se o grupo tem 4 pessoas, vocês precisarão de 8 entidades completas; se tiver 5, precisarão de 10.

**Estrutura de Pastas:** Já criem a estrutura base do projeto separando em pacotes: `model`, `view`, `controller` e `util` (para os arquivos de salvamento).


### 2. Modelagem das Classes (O Coração do Sistema)

O documento já dá uma excelente diretriz para a estrutura de um sistema de eventos. Vocês podem estruturar a camada de Modelo (Model) da seguinte forma para cumprir os requisitos de herança, abstração e polimorfismo:

*  **Classe Abstrata e Herança:** Criem uma classe abstrata `Pessoa`. A partir dela, façam as subclasses `Organizador` e `Participante`. Vocês também podem criar uma classe `Evento` com subclasses `EventoPresencial` e `EventoOnline` para demonstrar mais herança e polimorfismo.


* **Interface:** Implementem uma interface chamada `RelatorioGeravel`. As classes de Evento podem assinar esse contrato para gerar relatórios com estatísticas de participação e engajamento. Outra opção é uma interface `Checkinavel` para o controle de presença.


* **Coleções e Associações:** Um `Evento` poderá conter múltiplas `Sessoes`. Para relacionar quem vai em qual evento, o projeto sugere usar a coleção estruturada como `Map<Evento, List<Participante>>`.



### 3. Definindo os 3 CRUDs Mínimos

O sistema exige no mínimo 3 CRUDs (Cadastro, Alteração, Deleção e Listagem) que possuam relacionamento entre si. Para o sistema de eventos e ingressos, vocês podem usar:

*  **CRUD 1 (Pessoas):** Gerenciamento de `Participantes` e `Organizadores`.


* **CRUD 2 (Eventos e Sessões):** Onde os organizadores cadastram os eventos, definem se são online/presenciais e adicionam as sessões específicas.


* **CRUD 3 (Inscrições/Ingressos):** Onde ocorre a associação. O participante se inscreve em uma sessão ou compra um ingresso, gerando um histórico.



### 4. Persistência de Dados e Logs

* **Arquivos TXT ou JSON:** O banco de dados de vocês serão arquivos de texto ou JSON. Lembrem-se da regra da "barraquinha de cachorro quente": se o sistema fechar ou a luz cair, nenhum dado de ingresso ou cadastro pode ser perdido.


* **Log do Sistema:** É obrigatório criar um arquivo `.txt` separado apenas para Logs. Sempre que um evento for criado, um check-in for feito ou uma exceção (erro) ocorrer, gravem essa informação lá.


## Regras dos MVCs (A Matemática da Equipe)

O documento estabelece que o projeto deve conter 2 MVCs (Model, View e Controller) por pessoa do grupo. Sendo 5 integrantes, **o sistema precisará ter exatamente 10 classes Model (entidades), 10 Controllers e 10 Views**.

Para garantir que o sistema não fique simples demais e cumpra os requisitos de herança, abstração e os 3 CRUDs interligados, aqui está uma sugestão de 10 entidades lógicas para vocês dividirem:

1. **Organizador:** Subclasse que herda de uma classe abstrata `Pessoa`.
2. **Participante:** Subclasse que também herda de `Pessoa`.
3. **EventoPresencial:** Subclasse que herda de uma classe abstrata `Evento`.
4. **EventoOnline:** Subclasse que também herda de `Evento`.
5. **Sessao:** As palestras ou atividades específicas dentro de um evento maior.
6. **Inscricao:** Classe de associação que liga o `Participante` à `Sessao`.
7. **Local:** Gerencia a capacidade, endereço e salas dos eventos presenciais.
8. **Categoria:** Define o tipo do evento (ex: Tecnologia, Música, Educação).
9. **Certificado:** Gerado para o participante após a conclusão e check-in.
10. **Pagamento:** Gerencia a transação e o valor total do ingresso do participante.

>**Dica de Ouro:** Para evitar conflitos de código no GitHub, cada membro deve "adotar" 2 entidades inteiras. Por exemplo, o Membro 1 desenvolve tudo (Model, View, Controller e persistência) de `Participante` e `Inscricao`.

---

## Estrutura de Pastas (O Esqueleto do Projeto)

Utilizando os princípios de *Clean Code* e a arquitetura MVC, a estrutura de pacotes deve ser limpa e modular. Baseado no exemplo fornecido no documento, o projeto de vocês ficará assim:

* **/sistema_eventos**
* **/model:** Onde ficarão as 10 entidades citadas acima, além das classes abstratas base (`Pessoa` e `Evento`).
* **/interfaces:** Onde ficarão os contratos, como uma interface `RelatorioGeravel` ou `Autenticavel`.
* **/controller:** Onde ficarão os 10 controladores (ex: `ParticipanteController`, `SessaoController`), contendo toda a lógica de negócio, laços (`for`, `while`) e estruturas de controle (`if`, `switch`).
* **/view:** Onde ficarão as 10 classes de interface de usuário (menus no console, leitura de dados via `Scanner`).
* **/util:** Onde ficarão as classes utilitárias, como `ArquivoUtil` para salvar/ler arquivos em .txt/.json e a classe responsável por gerar o arquivo de Log do sistema.
* **Main.java:** O ponto de partida que inicializa o sistema e chama o menu principal.

Excelente estratégia! Ter a visão global da árvore do projeto antes de colocar a mão no código é a melhor forma de evitar refações e garantir que a equipe de 5 pessoas não se atropele.

Aqui está a árvore completa do seu Sistema de Gestão de Eventos, dividida em duas visões: a **Árvore de Hierarquia (Foco nas Superclasses e Herança)** e a **Árvore de Pastas (Foco na Arquitetura MVC)**.

### 1. Árvore de Hierarquia (O mapa das Superclasses)

Esta árvore mostra como as classes se relacionam através de herança (Superclasses e Subclasses) e quais implementarão as interfaces, atendendo diretamente às exigências do seu trabalho:

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

Esta é a estrutura exata que vocês devem criar no GitHub e na IDE (como VSCode ou IntelliJ) para organizar as 10 entidades (2 MVCs por pessoa) e seguir os princípios de *Clean Code*:

```text
sistema_eventos/
│
├── src/
│   ├── model/                  # Camada de Dados (10 entidades + 2 abstratas)
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
│   ├── controller/             # Lógica e Controle (10 Controllers)
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
│   ├── view/                   # Telas e Menus no Console (10 Views)
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
├── dados/                      # Pasta onde os arquivos .txt ou .json serão salvos
├── logs/                       # Pasta onde o log.txt será salvo
├── README.md                   # Documentação obrigatória do projeto
└── .gitignore                  # Para ignorar arquivos de compilação da IDE

```

Com essa árvore estruturada, o terreno está perfeitamente preparado para começar a codificar a base.