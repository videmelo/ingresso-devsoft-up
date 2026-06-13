# Sistema de Gestao de Eventos (ingresso-devsoft-up)

Este projeto consiste em um sistema completo de gestao de eventos academicos, corporativos e de entretenimento, desenvolvido como parte da disciplina de Desenvolvimento de Software na Universidade Positivo (2026.1).

O sistema e uma aplicacao de linha de comando (CLI) que utiliza os principais pilares da Programacao Orientada a Objetos (POO) e a arquitetura MVC (Model-View-Controller) para gerenciar o ciclo de vida de eventos, desde o cadastro de organizadores ate a emissao de certificados autenticados.

## Funcionalidades Principais

* Gestao de Pessoas: CRUD completo para Organizadores e Participantes.
* Gestao de Eventos: Suporte a eventos Online (com links de acesso) e Presenciais (com controle de lotacao e localizacao).
* Estrutura de Eventos: Criacao de sessoes (palestras/atividades), categorias e gestao de locais fisicos.
* Financeiro e Inscricoes: Fluxo de inscricao em sessoes, processamento de pagamentos com suporte a cupons de desconto.
* Execucao do Evento: Registro de check-in de participantes e geracao de relatorios de engajamento.
* Certificacao: Emissao de certificados em formato de texto com validacao de pagamento, presenca e codigo de autenticidade unico.
* Persistencia de Dados: Salvamento automatico de todos os dados em arquivos binarios (.dat).
* Logs do Sistema: Registro detalhado de operacoes e erros em arquivo de texto.

## Arquitetura e Padroes de Projeto

O sistema foi estruturado seguindo o padrao MVC, garantindo a separacao de responsabilidades:

* Model: Contem as entidades de dados, superclasses abstratas (Pessoa, Evento) e interfaces (Checkinavel, RelatorioGeravel).
* View: Gerencia toda a interacao com o usuario via console atraves de menus especializados.
* Controller: Responsavel pela logica de negocio, mediacao entre Model e View, e persistencia de dados.

### Conceitos de POO Aplicados:
* Heranca e Abstracao: Uso de classes abstratas para definir comportamentos comuns.
* Polimorfismo: Sobrecarga de metodos (ex: processarPagamento) e sobreposicao de metodos abstratos (ex: iniciarEvento).
* Interfaces: Contratos para comportamentos especificos como check-in e geracao de relatorios.
* Encapsulamento: Atributos privados com acesso via metodos Getters e Setters.
* Tratamento de Excecoes: Criacao de excecoes personalizadas para regras de negocio (ex: EventoLotadoException, PagamentoPendenteException).

## Tecnologias Utilizadas

* Linguagem: Java 17+
* Persistencia: Serializacao de objetos Java (java.io.Serializable) em arquivos .dat.
* Logs: FileWriter e PrintWriter para registro em tempo real.
* Interface: CLI (Command Line Interface).

## Estrutura de Pastas

```text
src/
├── controller/    # Logica de controle e persistencia (10 Controllers)
├── exceptions/    # Excecoes personalizadas do sistema
├── interfaces/    # Interfaces (Contratos)
├── model/         # Classes de modelo e entidades (10 Models)
├── util/          # Utilitarios de arquivo e logs
├── view/          # Menus e telas de console (10 Views)
└── Main.java      # Ponto de entrada da aplicacao
dados/             # Arquivos .dat (Persistencia)
logs/              # Arquivo log.txt
```

## Como Executar

1. Certifique-se de ter o JDK 17 ou superior instalado em sua maquina.
2. Clone o repositorio.
3. Compile os arquivos Java a partir da pasta src:
   javac src/**/*.java -d out
4. Execute a classe principal:
   java -cp out Main

## Equipe de Desenvolvimento

| Integrante | Responsabilidade |
| --- | --- |
| Vinicius | Modulo Pessoas (DEV 1) |
| Guilherme | Modulo Eventos (DEV 2) |
| Bruno | Modulo Estrutura (DEV 3) |
| Paulo | Modulo Financas (DEV 4) |
| Eduardo | Modulo Finalizacao (DEV 5) |

---
Projeto desenvolvido para fins academicos - 2026
