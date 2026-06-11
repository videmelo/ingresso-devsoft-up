# Diário de Bordo e Lista de Tarefas (DEVLOG)

Este documento acompanha o progresso das tarefas estabelecidas no Documento de Decisão de Arquitetura (`ADR.md`).

## 1. Configuração Inicial e Repositório

- [x] Criar o repositório do projeto no GitHub.
- [x] Adicionar os 5 desenvolvedores da equipe como colaboradores no repositório.
- [ ] Criar o arquivo `.gitignore` para ignorar arquivos de compilação (ex: `.class`, `.idea/`, `target/`).
- [ ] Criar a estrutura base de pacotes: `/src/model`, `/src/view`, `/src/controller`, `/src/interfaces`, `/src/exceptions` e `/src/util`.
- [ ] Criar os diretórios físicos na raiz do projeto: `dados/` (para armazenamento) e `logs/`.

## 2. Divisão de Responsabilidades (10 MVCs)

Cada desenvolvedor é responsável por implementar toda a camada de Model, View e Controller (incluindo persistência em arquivo) de 2 entidades.

| Desenvolvedor | MVC 1 | MVC 2 | Foco Principal |
| --- | --- | --- | --- |
| **Dev 1** | `Organizador` | `Participante` | Pessoas, herança da classe base `Pessoa` e interface `Checkinavel`. |
| **Dev 2** | `EventoPresencial` | `EventoOnline` | Eventos físicos e digitais, herança de `Evento` e interface `RelatorioGeravel`. |
| **Dev 3** | `Sessao` | `Local` | Estrutura de tempo e espaço dos eventos. |
| **Dev 4** | `Inscricao` | `Pagamento` | Associação entre pessoas e eventos, polimorfismo de sobrecarga no pagamento. |
| **Dev 5** | `Categoria` | `Certificado` | Classificação de eventos e emissão baseada em validação. |

## 3. Modelagem Base e Contratos (Fundação)

- [ ] Implementar a interface `Checkinavel` e `RelatorioGeravel`.
- [ ] Implementar a classe abstrata `Pessoa` e a classe abstrata `Evento` (implementando `RelatorioGeravel`).
- [ ] Estabelecer os construtores, métodos `get/set` e a sobrescrita do método `toString()` em todas as 10 entidades concretas.

## 4. Implementação dos Controllers (CRUDs e Regras de Negócio)

- [ ] **CRUD 1 (Pessoas):** Finalizar cadastro, listagem, atualização e exclusão em `OrganizadorController` e `ParticipanteController`.
- [ ] **CRUD 2 (Eventos/Estrutura):** Finalizar métodos base e a lógica para aprovar eventos e vincular sessões aos eventos.
- [ ] **CRUD 3 (Inscrições/Financeiro):** Finalizar a lógica de `InscricaoController` e `PagamentoController` (incluindo regra de descontos e alteração de status).

## 5. Persistência de Dados e Utilitários

- [ ] Criar a classe `ArquivoUtil.java` para lidar com a gravação/leitura de arquivos `.txt` ou `.json`.
- [ ] Implementar as chamadas `salvarDadosArquivo()` e `carregarDadosArquivo()` dentro de todos os 10 Controllers, utilizando listas em memória.
- [ ] Criar a classe `LogUtil.java` formatando a saída obrigatoriamente como `[DATA/HORA] - [NÍVEL] - [MENSAGEM]`.

## 6. Exceções e Padrões Avançados

- [ ] Criar as exceções personalizadas (ex: `EntidadeNaoEncontradaException`, `EventoLotadoException`, `PagamentoPendenteException`).
- [ ] Implementar blocos `try/catch` nas Views para tratamento de entrada inválida (ex: `InputMismatchException` no `Scanner`).
- [ ] Integrar o registro de logs nas ações principais de sucesso (`[INFO]`), regras bloqueadas (`[WARN]`) e falhas técnicas (`[ERROR]`).

## 7. Interface Gráfica e Finalização

- [ ] Desenvolver a classe `MenuPrincipal.java` contendo as 4 opções de navegação e a opção de salvar/sair (conforme mapa no `ADR.md`).
- [ ] Validar a integração de todos os submenus nos respectivos Controllers.
- [ ] Elaborar o arquivo `README.md` final com instruções de compilação, execução e créditos da equipe.