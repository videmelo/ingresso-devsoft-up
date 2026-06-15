# Lista de Tarefas (DEVLOG)

Este documento estabelece o fluxo de trabalho (Workflow) da equipe e acompanha o progresso do Sistema de Gestão de Eventos, conforme as diretrizes do ADR.md.

---

## FASE 1: Fundação do Projeto (Responsabilidade: Equipe)

- [x] Criar repositório no GitHub e adicionar os 5 colaboradores.
- [x] Criar o arquivo .gitignore (ignorar .class, .idea, target, etc).
- [x] Criar as pastas base do MVC: /src/model, /src/view, /src/controller, /src/interfaces, /src/exceptions, /src/util.
- [x] Criar arquivos base: Pessoa.java (Abstrata) e Evento.java (Abstrata).
- [x] Criar interfaces: Checkinavel.java e RelatorioGeravel.java.
- [x] Criar pacote de Exceções (EventoLotadoException, PagamentoPendenteException, etc).
- [x] Criar classes de suporte em /util: ArquivoUtil.java e LogUtil.java.
- [x] Criar classe Main.java e o esqueleto vazio do MenuPrincipal.java.

---

## Atribuição da Equipe

| Código | Nome do Integrante | Módulo Assumido | Status |
| --- | --- | --- | --- |
| **DEV 1** | Vinicius | Pessoas | Concluído |
| **DEV 2** | Guilherme | Eventos | Concluído |
| **DEV 3** | Bruno | Estrutura | Concluído |
| **DEV 4** | Paulo | Finanças | Concluído |
| **DEV 5** | Eduardo | Finalização | Concluído |

---

## FASE 2: Desenvolvimento Paralelo (Responsabilidade: Devs 1 a 5)

### Tarefas do DEV 1 (Módulo Pessoas)
- [x] Model: Organizador e Participante.
- [x] Controller: OrganizadorController e ParticipanteController.
- [x] View: OrganizadorView e ParticipanteView.

### Tarefas do DEV 2 (Módulo Eventos)
- [x] Model: EventoPresencial e EventoOnline.
- [x] Controller: EventoPresencialController e EventoOnlineController.
- [x] View: EventoPresencialView e EventoOnlineView.

### Tarefas do DEV 3 (Módulo Estrutura)
- [x] Model: Sessao e Local.
- [x] Controller: SessaoController e LocalController.
- [x] View: SessaoView e LocalView.

### Tarefas do DEV 4 (Módulo Vínculos e Finanças)
- [x] Model: Inscricao e Pagamento.
- [x] Controller: InscricaoController e PagamentoController.
- [x] View: InscricaoView e PagamentoView.

### Tarefas do DEV 5 (Módulo Classificação e Finalização)
- [x] Model: Categoria e Certificado.
- [x] Controller: CategoriaController e CertificadoController.
- [x] View: CategoriaView e CertificadoView.

---

## FASE 3: Integração e Entrega (Responsabilidade: Equipe)

- [x] Merge: Todas as funcionalidades integradas na main.
- [x] Menu Principal (View): Conectado a todas as sub-views.
- [x] Salvar e Sair: Implementado em todos os 10 Controllers.
- [x] Logs: Configurados para registrar ações de todos os módulos.
- [x] Testes Finais: Rodar o sistema do início ao fim.
- [x] Documentação: Preencher o README.md final.

---

## FASE 4: Refatoração Lógica e Correções (Responsabilidade: IA Gemini)

- [x] **Correção de Histórico de Inscrições:** Corrigido o bug onde as inscrições recém-criadas não eram adicionadas à lista do Participante.
- [x] **Lógica de Capacidade e Lotação:** Adicionado o decremento de lotação de eventos presenciais ao cancelar inscrições.
- [x] **Segurança Financeira:** Removido input de preço solto no pagamento. A classe `Sessao` agora possui o atributo `preco`, injetado automaticamente na criação do `Pagamento`. Impede fraudes de preço livre e pagamento de inscrições não-pendentes.
- [x] **Arquitetura de Check-in:** A responsabilidade de check-in (`Checkinavel`) e o status de presença foram transferidos do `Participante` para a `Inscricao` (`compareceu`), solucionando a falha de estado global onde o participante ficaria presente em todos os eventos após o primeiro check-in.
- [x] **Controle Físico (Locais):** Refatoração do `EventoPresencial` para receber um objeto `Local` em seu construtor ao invés de uma `String`.
- [x] **Segurança Organizacional (RBAC):** Reforçada a obrigatoriedade da identificação de um `Organizador` para criar eventos. Eventos criados por nível "Comum" agora ficam como "Agendado", necessitando da aprovação ("Aprovado") de um Organizador "Admin".
- [x] **Emissão Segura de Certificados:** Adicionada trava de segurança impedindo a emissão de certificado para participante ausente ou inadimplente.
