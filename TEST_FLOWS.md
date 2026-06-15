# Roteiro de Testes do Sistema

Este documento descreve 3 fluxos principais para validar a integracao de todos os modulos do sistema (Pessoas, Eventos, Estrutura, Financas e Finalizacao).

## Fluxo 1: Ciclo Completo de Participacao (Inscricao ate Certificado)
Objetivo: Validar o elo entre Participante, Sessao, Pagamento e Emissao de Certificado.

1. Gerenciar Pessoas: Cadastrar um novo Participante (ID: P1).
2. Gerenciar Pessoas: Cadastrar um Organizador Admin (ID: O1).
3. Gerenciar Eventos e Estrutura:
   - Cadastrar uma Categoria (ID: C1).
   - Cadastrar um Evento Online (ID: E1), inserindo o ID do Organizador O1 para aprovação.
   - Cadastrar uma Sessao (ID: S1) vinculada ao Evento E1 e definir o preço da inscrição (ex: 100.0).
4. Financeiro e Inscricoes:
   - Realizar Inscricao do Participante P1 na Sessao S1 (ID da Inscricao: I1). O status sera "Pendente".
   - Gerenciar Pagamentos: Processar Pagamento para a Inscricao I1. O sistema deve cobrar 100.0 automaticamente. Aplicar cupom "DESCONTO10".
   - Verificar se o status da Inscricao I1 mudou para "Confirmada".
5. Execucao do Evento:
   - Realizar Check-in para a Inscrição I1 do Participante P1.
   - Aprovar e Iniciar Evento E1 (via Menu Execução ou automatico).
   - Gerenciar Certificados: Emitir Certificado para o Participante P1 no Evento E1.
   - Validar se o certificado e exibido com o codigo de autenticidade.

## Fluxo 2: Gestao de Evento Presencial e Lotacao
Objetivo: Validar a alocacao de locais fisicos e a trava de seguranca de capacidade maxima.

1. Gerenciar Pessoas: Cadastrar um Organizador Admin (ID: O2).
2. Gerenciar Eventos e Estrutura:
   - Cadastrar um Local Físico (ID: L1).
   - Cadastrar um Evento Presencial (ID: E2), fornecendo o Organizador O2, selecionando o Local L1, e definindo a capacidade como 2.
   - Cadastrar uma Sessao (ID: S2) vinculada ao Evento E2 (Definir preço 0).
3. Gerenciar Pessoas: Cadastrar 3 Participantes diferentes (P1, P2 e P3).
4. Financeiro e Inscricoes:
   - Realizar Inscricao do Participante P1 na Sessao S2. (Sucesso)
   - Realizar Inscricao do Participante P2 na Sessao S2. (Sucesso)
   - Realizar Inscricao do Participante P3 na Sessao S2.
   - Validar se o sistema lanca o erro "Evento lotado" ou impede a terceira inscricao.
5. Execucao do Evento:
   - Gerar Relatorios e verificar se a contagem de inscritos para o Evento E2 e igual a 2.

## Fluxo 3: Administracao e Manutencao
Objetivo: Validar as funcoes de edicao, exclusao e persistencia de dados.

1. Gerenciar Pessoas: Cadastrar um Organizador (ID: O1).
2. Gerenciar Pessoas -> Atualizar Dados: Alterar o nome ou e-mail do Organizador O1.
3. Gerenciar Eventos e Estrutura -> Gerenciar Sessoes:
   - Cadastrar uma Sessao (ID: S3).
   - Utilizar a opcao "Reagendar Sessao" para alterar o horario.
4. Salvar e Sair: Escolher a opcao 0 para persistir os dados.
5. Reiniciar o Sistema:
   - Abrir o sistema novamente.
   - Listar Pessoas e Sessoes para validar se as alteracoes (nome atualizado e novo horario) foram carregadas corretamente dos arquivos .dat.
