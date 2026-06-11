## To-Do List: Sistema de Gestão de Eventos

### 1. Configuração Inicial e Repositório

* [X] Criar o repositório do projeto no GitHub.
* [X] Adicionar os 5 membros da equipe como colaboradores no repositório.
* [ ] Criar o arquivo `.gitignore` para não subir arquivos de compilação da IDE.
* [ ] Criar a estrutura base de pacotes na IDE: `model`, `view`, `controller`, `interfaces` e `util`.
* [ ] Criar as pastas físicas na raiz do projeto: `dados/` (para txt/json) e `logs/`.

---

### 2. Divisão de Responsabilidades (2 MVCs por Integrante)

Cada membro ficará responsável por desenvolver o Model, a View, o Controller e a persistência de dados das suas duas entidades, garantindo que não haja conflitos no código.

| Integrante | MVC 1 (Model, View, Controller) | MVC 2 (Model, View, Controller) | Foco Principal da Tarefa |
| --- | --- | --- | --- |
| **Membro 1** | `Organizador` | `Participante` | Gerenciamento de usuários e herança de `Pessoa`. |
| **Membro 2** | `EventoPresencial` | `EventoOnline` | Criação de eventos e herança de `Evento`. |
| **Membro 3** | `Sessao` | `Local` | Logística e infraestrutura física/grade do evento. |
| **Membro 4** | `Inscricao` | `Pagamento` | Associação entre participante, evento e financeiro. |
| **Membro 5** | `Categoria` | `Certificado` | Classificação dos eventos e geração de certificados. |

---

### 3. Modelagem Base e Contratos (Trabalho Conjunto ou Líder)

* [ ] Criar a superclasse abstrata `Pessoa` (atributos comuns a organizadores e participantes).
* [ ] Criar a superclasse abstrata `Evento` (atributos comuns a eventos presenciais e online).
* [ ] Criar a interface `RelatorioGeravel` (com método para gerar estatísticas).
* [ ] Criar a interface `Checkinavel` (com método para controle de presença).
* [ ] Implementar a herança nas classes filhas (`Organizador` e `Participante` extends `Pessoa`; `EventoPresencial` e `EventoOnline` extends `Evento`).

---

### 4. Implementação dos 3 CRUDs Mínimos e Relacionamentos

* [ ] **CRUD 1:** Finalizar métodos de Cadastrar, Alterar, Deletar e Listar Pessoas (Organizadores e Participantes).
* [ ] **CRUD 2:** Finalizar métodos de Cadastrar, Alterar, Deletar e Listar Eventos e Sessões.
* [ ] **CRUD 3:** Finalizar métodos de Cadastrar, Alterar, Deletar e Listar Inscrições.
* [ ] Implementar a associação de coleções (Ex: `Map<Evento, List<Participante>>`).

---

### 5. Utilitários e Persistência de Dados

* [ ] Criar a classe `ArquivoUtil.java` para salvar e ler os dados.
* [ ] Implementar a persistência em `.txt` ou `.json` em todos os Controllers (garantir a regra da "barraquinha de cachorro quente").
* [ ] Criar a classe `LogUtil.java` para registro de atividades.
* [ ] Inserir chamadas de log sempre que um evento for criado, check-in for feito ou erro/exceção ocorrer.

---

### 6. Interface Gráfica e Finalização

* [ ] Criar o `MenuPrincipal.java` para unificar a chamada das Views de todos os integrantes.
* [ ] Validar o uso de estruturas de controle (`if`, `switch`) e laços (`for`, `while`) no código.
* [ ] Realizar testes integrados (um membro testando a tela do outro).
* [ ] Escrever o arquivo `README.md` com as instruções de como rodar o projeto e explicação sobre o uso de IA.