# Roadmap de desenvolvimento

Este roadmap organiza o crescimento do projeto em fases claras, com foco em produto, qualidade técnica e evolução contínua.

## Fase 1 — Fundação e produto
**Status:** concluída parcialmente

Objetivos:
- definir a proposta do aplicativo;
- consolidar o MVP;
- documentar a visão e os requisitos iniciais;
- estabelecer a estrutura inicial do repositório.

Entregas:
- README profissional;
- especificação do MVP;
- arquitetura inicial do projeto;
- protótipo de lógica em Python.

## Fase 2 — Estrutura Android e interface inicial
**Status:** em andamento

Objetivos:
- criar a base do app Android;
- organizar a estrutura de pastas e módulos;
- implementar a interface inicial do fluxo principal;
- validar a experiência do usuário.

Entregas:
- projeto Android com Kotlin e Jetpack Compose;
- tela inicial e navegação básica;
- layout compacto e expandido;
- configuração inicial de tema e componentes reutilizáveis.

## Fase 3 — Motor de cálculo e regras de negócio
**Status:** em desenvolvimento

Objetivos:
- consolidar o modelo de corrida;
- centralizar as regras de avaliação de rentabilidade;
- integrar cálculo de combustível, distância e R$/KM.

Entregas:
- modelo de domínio robusto;
- calculadora principal;
- validações e regras de negócio;
- testes unitários básicos.

## Fase 4 — Persistência e configuração do usuário
**Status:** planejada

Objetivos:
- armazenar dados do veículo e do combustível;
- permitir personalização da experiência;
- melhorar a usabilidade do app.

Entregas:
- armazenamento local de configurações;
- telas de configuração do usuário;
- persistência de preferências.

## Fase 5 — Integração com notificações e overlay
**Status:** planejada

Objetivos:
- capturar contexto de corrida em tempo real;
- exibir informações de forma não intrusiva;
- preparar a experiência para uso prático em tela completa.

Entregas:
- leitura de notificações;
- overlay flutuante;
- integração com plataformas principais.

## Fase 6 — Qualidade, testes e evolução
**Status:** planejada

Objetivos:
- fortalecer a confiabilidade do app;
- melhorar a manutenção do código;
- preparar a base para evolução comercial e futura expansão.

Entregas:
- testes automatizados;
- refatoração contínua;
- melhorias de UX e performance;
- documentação técnica mais madura.

## Critérios de conclusão do MVP

O MVP será considerado pronto quando:
- o fluxo principal de análise da corrida estiver funcional;
- a interface atender ao objetivo de decisão rápida;
- a lógica de cálculo estiver testada e confiável;
- a experiência for clara e intuitiva para o usuário.

## Roteiro enumerado para finalizacao do app

### Sprint 3 - Confiabilidade e integracao base

1. Congelar contrato de dominio
- Definir contrato unico para Corrida, validacoes e classificacao.
- Sincronizar regras entre Python (prototipo) e Android (Kotlin).

Critério de aceite:
- documento de contrato revisado e versionado em docs;
- regras sem divergencia entre modulos de calculo/classificacao.

2. Expandir testes do pipeline de notificacoes
- Cobrir casos truncados, variacoes regionais e erros de extração.
- Manter suite executavel por comando unico.

Critério de aceite:
- cobertura das regras centrais >= 85%;
- todos os testes verdes em CI/local.

3. Implementar NotificationListenerService no Android
- Capturar notificacoes reais das plataformas suportadas.
- Transformar payload bruto em NotificationData Android.

Critério de aceite:
- servico ativo com permissao concedida;
- notificacoes reais visiveis em log estruturado.

4. Integrar detector + parser no app Android
- Conectar leitura da notificacao ao motor de decisao.
- Tratar plataforma nao suportada sem quebrar UX.

Critério de aceite:
- notificacao suportada gera Corrida valida;
- erro conhecido gera fallback amigavel (sem crash).

### Sprint 4 - Produto utilizavel em campo

5. Persistencia de configuracoes do motorista
- Salvar veiculo, combustivel e preferencias com DataStore/Room.

Critério de aceite:
- configuracoes persistem entre reinicios;
- tela abre com estado restaurado corretamente.

6. Overlay e tela de decisao em tempo real
- Exibir valor, km total, R$/km, custo e classificacao.
- Otimizar legibilidade para decisao em poucos segundos.

Critério de aceite:
- informacoes atualizam em menos de 1 segundo apos notificacao;
- leitura visual aprovada em teste manual de uso.

7. Observabilidade e tratamento de erro
- Padronizar logs e codigos de erro do pipeline.
- Registrar falhas de parse para depuracao posterior.

Critério de aceite:
- erros com contexto minimo (plataforma, motivo, trecho da mensagem);
- zero falhas silenciosas no fluxo principal.

8. Performance e bateria
- Medir latencia de parse e impacto em background.
- Ajustar frequencia/processamento para consumo eficiente.

Critério de aceite:
- latencia media parse->resultado <= 300 ms;
- sem degradacao perceptivel no uso continuo.

### Sprint 5 - Fechamento para publicacao

9. Qualidade final Android
- Adicionar testes unitarios e instrumentados essenciais.
- Executar checklist final de estabilidade.

Critério de aceite:
- fluxo principal coberto por testes automatizados;
- sem crash em cenarios validos do MVP.

10. Documentacao e apresentacao para recrutadores
- Atualizar README com demo real (GIF/video) e arquitetura final.
- Publicar release notes por sprint e instrucoes de execucao.

Critério de aceite:
- onboarding tecnico em ate 10 minutos;
- repositorio com narrativa clara de produto e engenharia.

11. Beta fechado com motoristas
- Distribuir build para pequeno grupo de validacao.
- Coletar feedback de clareza, velocidade e confianca na decisao.

Critério de aceite:
- feedback consolidado em backlog priorizado;
- ajustes criticos aplicados antes da versao candidata.

12. Release candidata (RC1)
- Congelar escopo MVP.
- Executar regressao final e gerar pacote para divulgacao.

Critério de aceite:
- RC1 estavel, documentada e demonstravel end-to-end.

## Definicao de pronto (DoD) por etapa

- codigo com testes atualizados e passando;
- documentacao da etapa atualizada em docs;
- sem erros silenciosos no fluxo principal;
- validacao manual registrada com evidencias.
