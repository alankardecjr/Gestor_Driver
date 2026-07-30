# Execution Board - Gestor Driver

Board simplificado para acompanhar a execucao das proximas sprints ate a RC1.
Atualize este arquivo ao final de cada sessao de desenvolvimento.

## To Do

1. [S3] Definir contrato unico de Corrida e regras de classificacao.
2. [S3] Expandir testes do pipeline (truncadas, variacoes regionais, erros).
3. [S3] Implementar NotificationListenerService no Android.
4. [S3] Integrar detector/parser ao fluxo Android com fallback seguro.
5. [S4] Persistir configuracoes do motorista (DataStore/Room).
6. [S4] Finalizar overlay de decisao em tempo real.
7. [S4] Padronizar observabilidade e tratamento de erro.
8. [S4] Medir e otimizar performance e consumo.
9. [S5] Fechar testes Android (unitarios + instrumentados).
10. [S5] Preparar material final para recrutadores (README + demo + release notes).
11. [S5] Rodar beta fechado e consolidar feedback.
12. [S5] Gerar RC1.

## Doing

1. Nenhuma tarefa em andamento no momento.

## Done

1. Sprint 2 concluida: pipeline notifications completo em Python.
2. Testes unitarios iniciais do pipeline criados e aprovados.
3. Demo executavel de notificacoes criada.
4. Documentacao de dominio no core atualizada.
5. Roadmap detalhado de finalizacao registrado.

## Foco da semana (sugestao)

1. Fechar contrato unico de dominio e validar divergencias de classificacao.
2. Subir cobertura dos testes do pipeline para no minimo 85% nas regras centrais.
3. Iniciar integracao Android com NotificationListenerService.

## Criterios de saida da Sprint 3

1. NotificationListenerService ativo e recebendo notificacoes reais.
2. Notificacao suportada gera Corrida valida sem crash.
3. Erros de plataforma nao suportada tratados com fallback amigavel.
4. Suite de testes da camada de regras verdes localmente.
