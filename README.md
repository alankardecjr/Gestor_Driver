# Gestor Driver

<p align="center">
  <img src="https://img.shields.io/badge/Android-Kotlin-blue" alt="Android Kotlin" />
  <img src="https://img.shields.io/badge/Status-MVP%20em%20desenvolvimento-orange" alt="Status do projeto" />
  <img src="https://img.shields.io/badge/Arquitetura-Compose%20%2B%20MVVM-success" alt="Arquitetura" />
</p>

Gestor Driver é um projeto de aplicativo Android voltado para motoristas de aplicativos que precisam tomar decisões rápidas e mais rentáveis ao aceitar uma corrida. A proposta central é transformar dados dispersos de uma corrida em informações objetivas, permitindo análise imediata de lucratividade antes da aceitação.

<p align="center">
  <strong>🚧 MVP em desenvolvimento 🚧</strong>
</p>

## Visão geral

O projeto nasceu para resolver um problema real: ajudar o motorista a responder, em poucos segundos, a pergunta: "vale a pena aceitar esta corrida?"

A solução combina:
- análise de dados da corrida;
- cálculo de rentabilidade;
- interface compacta e objetiva;
- suporte futuro a notificações e overlay flutuante.

## Problema e proposta de valor

Motoristas de aplicativos frequentemente precisam decidir rapidamente, sem tempo para analisar todas as variáveis envolvidas. O Gestor Driver oferece uma camada de apoio inteligente que apresenta indicadores-chave como:
- valor da corrida;
- distância total;
- custo operacional estimado;
- consumo estimado;
- indicador visual de rentabilidade.

### Valor entregue

O projeto busca reduzir a incerteza na tomada de decisão e oferecer uma experiência mais objetiva, clara e útil no momento de aceitar uma corrida.

## Funcionalidades do MVP

- leitura de dados da corrida;
- identificação da plataforma de origem;
- cálculo de distância total;
- cálculo de valor por quilômetro (R$/KM);
- estimativa de consumo e custo operacional;
- interface compacta e expandida;
- configuração de veículo e combustível;
- fluxo inicial preparado para futuras integrações com notificações e overlay.

## Sprint 2 - Sistema de leitura de corridas

O projeto agora possui um pipeline funcional de leitura de notificacoes,
mantendo a inteligencia desacoplada do Android:

NotificationData -> PlatformDetector -> Parsers (Uber/99/inDrive)
-> Validator -> Corrida -> CalculadoraCorrida.

Com isso, o nucleo de decisao ja pode ser testado localmente sem depender
do app Android em execucao.

## Arquitetura do projeto

O projeto está estruturado em duas frentes principais:

1. Prototipação em Python
   - validação rápida da lógica de negócio;
   - modelagem inicial de corrida e cálculos.

2. Aplicativo Android
   - implementação futura em Kotlin com Jetpack Compose;
   - foco em experiência de usuário, modularização e manutenção.

## Stack tecnológica

### Android
- Kotlin
- Jetpack Compose
- Material 3
- Gradle
- Android Studio

### Prototipação e lógica
- Python
- dataclasses e modelos simples de domínio

## Estrutura do repositório

```text
Gestor-Driver/
├── android-app/           # projeto Android
├── app/                   # protótipo em Python
├── core/                  # lógica de domínio e cálculo
├── docs/                  # documentação técnica
├── README.md              # visão geral do projeto
├── Roadmap.md             # planejamento de desenvolvimento
├── config.md              # configuração do ambiente
└── *.md                   # especificações e documentação complementar
```

## Como executar

### Pré-requisitos
- Android Studio
- JDK 17 ou superior
- Python 3.10+
- Gradle Wrapper

### Executar o protótipo Python

```bash
python app/main.py
```

### Executar demo do pipeline de notificacoes

```bash
python app/notifications_demo.py
```

### Executar testes unitarios (pipeline de notificacoes)

```bash
python -m unittest discover -s tests -p "test_*.py"
```

### Executar o projeto Android

```bash
cd android-app
./gradlew assembleDebug
```

## Boas práticas aplicadas

- separação clara entre lógica de domínio e interface;
- documentação técnica centralizada;
- estrutura preparada para evolução para arquitetura MVVM;
- foco em legibilidade, manutenção e escalabilidade.

## Status atual

- documentação inicial: concluída;
- protótipo de lógica: em evolução;
- aplicativo Android: em fase inicial;
- integração com notificações e overlay: planejada.

## Próximos passos

1. consolidar a interface inicial do app;
2. integrar o motor de cálculo ao fluxo Android;
3. implementar persistência de configurações;
4. evoluir para overlays e notificações contextuais.

## Roadmap resumido

- fase 1: definição do produto e MVP;
- fase 2: estrutura Android e interface inicial;
- fase 3: motor de cálculo e configurações;
- fase 4: integração com notificações e overlay;
- fase 5: expansão para mais plataformas e melhoria de experiência.

## Documentação adicional

- [CONTRIBUTING.md](CONTRIBUTING.md)
- [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)
- [docs/DEVELOPMENT_GUIDE.md](docs/DEVELOPMENT_GUIDE.md)
- [docs/TESTING_STRATEGY.md](docs/TESTING_STRATEGY.md)

## Contato e contexto

Este projeto está sendo desenvolvido com foco em produto, arquitetura e evolução técnica, com visão de crescimento real para um aplicativo de alto valor prático.
