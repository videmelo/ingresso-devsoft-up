# Lista de Tarefas (DEVLOG)

Este documento estabelece o fluxo de trabalho (Workflow) da equipe e acompanha o progresso do Sistema de Gestão de Eventos, conforme as diretrizes do `ADR.md`.

> **A Melhor Estratégia de Desenvolvimento:** Como a equipe de 5 desenvolvedores não trabalhará simultaneamente na mesma tela, o desenvolvimento foi dividido em **3 Fases**. A Fase 1 deve ser concluída por um membro designado pela equipe (para evitar conflitos de código base). Após a Fase 1 ser finalizada e consolidada na branch `main`, os 5 Devs iniciarão a Fase 2 paralelamente, cada um em sua própria branch (ex: `feature/dev1`).

---

## FASE 1: Fundação do Projeto (Responsabilidade: Equipe)

Um membro designado fará o setup inicial. Nenhum outro Dev deve começar a programar até que esta fase esteja concluída e mesclada na `main`. Isso garante que todos terão acesso às classes Abstratas e Utilitários base para trabalhar.

- [x] Criar repositório no GitHub e adicionar os 5 colaboradores.
- [x] Criar o arquivo `.gitignore` (ignorar `.class`, `.idea`, `target`, etc).
- [x] Criar as pastas base do MVC: `/src/model`, `/src/view`, `/src/controller`, `/src/interfaces`, `/src/exceptions`, `/src/util`.
- [x] Criar arquivos base: `Pessoa.java` (Abstrata) e `Evento.java` (Abstrata).
- [x] Criar interfaces: `Checkinavel.java` e `RelatorioGeravel.java`.
- [x] Criar pacote de Exceções (`EventoLotadoException`, `PagamentoPendenteException`, etc).
- [x] Criar classes de suporte em `/util`: `ArquivoUtil.java` e `LogUtil.java` (configurada para `[DATA/HORA] - [NÍVEL] - [MENSAGEM]`).
- [x] Criar classe `Main.java` e o esqueleto vazio do `MenuPrincipal.java` (apenas com o layout do console desenhado).

---

## Atribuição da Equipe

Antes de iniciar a Fase 2, a equipe deve preencher os nomes abaixo para oficializar quem será responsável por qual módulo.

| Código | Nome do Integrante | Módulo Assumido | Nome da Branch Sugerida |
| --- | --- | --- | --- |
| **DEV 1** | `Vinicius` | Pessoas | `feature/dev1-pessoas` |
| **DEV 2** | `Guilherme` | Eventos | `feature/dev2-eventos` |
| **DEV 3** | `[Preencher Nome]` | Estrutura | `feature/dev3-estrutura` |
| **DEV 4** | `[Preencher Nome]` | Finanças | `feature/dev4-financas` |
| **DEV 5** | `[Preencher Nome]` | Finalização | `feature/dev5-finalizacao` |

---

## FASE 2: Desenvolvimento Paralelo (Responsabilidade: Devs 1 a 5)

Cada Desenvolvedor deve criar sua branch (ex: `git checkout -b feature/dev1`) a partir da `main` atualizada. A tarefa individual consiste em implementar **Model, View e Controller (incluindo leitura/gravação de objetos em .dat)** apenas para as suas 2 entidades.

### Tarefas do DEV 1 (Módulo Pessoas)
*Entidades:* `Organizador` e `Participante`
- [x] **Model:** Criar as classes estendendo `Pessoa`.
- [x] **Model:** Implementar a interface `Checkinavel` no `Participante` (lógica de realizar check-in).
- [x] **Controller:** Criar `OrganizadorController` e `ParticipanteController` (Implementar o CRUD 1 completo e os métodos de ler/salvar arquivo).
- [x] **View:** Criar `OrganizadorView` e `ParticipanteView` contendo o Scanner para cadastrar, listar, atualizar e deletar.
- [x] **Integração Base:** Lidar com a exceção `EntidadeNaoEncontradaException` na busca do CRUD.

### Tarefas do DEV 2 (Módulo Eventos Físicos e Digitais)
*Entidades:* `EventoPresencial` e `EventoOnline`
- [x] **Model:** Criar as classes estendendo `Evento`.
- [x] **Model:** Implementar lógica de `verificarLotacao()` (lançando `EventoLotadoException`) e `enviarLinkAcesso()`.
- [x] **Controller:** Criar `EventoPresencialController` e `EventoOnlineController` (CRUD 2 base e métodos de ler/salvar arquivo).
- [x] **View:** Criar `EventoPresencialView` e `EventoOnlineView` com Scanner.
- [x] **Integração Base:** Garantir o polimorfismo do método abstrato `iniciarEvento()`.

### Tarefas do DEV 3 (Módulo Estrutura)
*Entidades:* `Sessao` e `Local`
- [ ] **Model:** Criar classes `Sessao` (com método de reagendamento) e `Local` (validação de disponibilidade).
- [ ] **Controller:** Criar `SessaoController` e `LocalController` (Continuar o CRUD 2 e os métodos de ler/salvar arquivo).
- [ ] **View:** Criar `SessaoView` e `LocalView`.
- [ ] **Integração Base:** Garantir que uma Sessão consiga receber/vincular um Evento (associação).

### Tarefas do DEV 4 (Módulo Vínculos e Finanças)
*Entidades:* `Inscricao` e `Pagamento`
- [ ] **Model:** Criar classe `Inscricao` (com métodos de confirmar/cancelar status).
- [ ] **Model:** Criar classe `Pagamento` implementando Polimorfismo de Sobrecarga em `processarPagamento(cupom)`.
- [ ] **Controller:** Criar `InscricaoController` e `PagamentoController` (CRUD 3 completo e ler/salvar arquivo).
- [ ] **View:** Criar `InscricaoView` e `PagamentoView` com Scanner.
- [ ] **Integração Base:** Garantir o fluxo em que Inscrição só fica "Confirmada" após Pagamento aprovar.

### Tarefas do DEV 5 (Módulo Classificação e Finalização)
*Entidades:* `Categoria` e `Certificado`
- [ ] **Model:** Criar classe `Categoria` (método de atualizar descrição).
- [ ] **Model:** Criar classe `Certificado` implementando emissão com validação (lançar `PagamentoPendenteException` se necessário) e geração de hash de autenticidade.
- [ ] **Controller:** Criar `CategoriaController` e `CertificadoController` (CRUDs de apoio e ler/salvar arquivo).
- [ ] **View:** Criar `CategoriaView` e `CertificadoView` com Scanner.
- [ ] **Integração Base:** Formatador visual do diploma/certificado para o console.

---

## FASE 3: Integração e Entrega (Responsabilidade: Equipe)

Após os Devs finalizarem a Fase 2, abrirão Pull Requests (PRs). A equipe revisará o código e fará o merge de tudo para a `main`.

- [ ] **Merge:** Juntar as branches `feature/dev1` a `feature/dev5` na `main` resolvendo possíveis conflitos.
- [ ] **Menu Principal (View):** Conectar os menus (Opções 1, 2, 3 e 4) desenhados na Fase 1 para chamarem as Views construídas por cada Dev (ex: A Opção 1.1 chama `OrganizadorView.exibirMenuCadastro()`).
- [ ] **Salvar e Sair:** Ligar a "Opção 0" do `MenuPrincipal` aos métodos `salvarDadosArquivo()` de todos os 10 Controllers ao mesmo tempo.
- [ ] **Testes Finais:** Rodar o sistema do início ao fim (Cadastrar pessoa -> Cadastrar evento -> Inscrever -> Pagar -> Fazer Check-in -> Emitir Certificado).
- [ ] **Logs:** Verificar se o arquivo `log.txt` está sendo gerado corretamente com os níveis INFO, WARN e ERROR.
- [ ] **Documentação:** Preencher o `README.md` final com o nome dos integrantes e as instruções de uso.
