# Gestor Driver — Especificação do MVP v1.1

Este documento descreve o escopo inicial do produto, seus objetivos, regras de negócio e critérios de aceitação para o MVP.

## 1. Objetivo do produto

Auxiliar motoristas de aplicativos a avaliar rapidamente se uma corrida é financeiramente vantajosa antes de aceitá-la.

## 2. Público-alvo

Motoristas de aplicativos que desejam tomar decisões mais estratégicas e reduzir desperdício de tempo e combustível.

## 3. Problema central

O motorista precisa decidir rapidamente com base em informações incompletas e dispersas. O aplicativo deve consolidar essas informações em uma análise objetiva.

## 4. Funcionalidades do MVP

### 4.1 Análise da corrida
- calcular distância total;
- calcular valor por quilômetro (R$/KM);
- estimar consumo e custo operacional;
- gerar uma classificação visual de rentabilidade.

### 4.2 Configurações do usuário
- veículo: marca, modelo, versão e ano;
- consumo por combustível;
- preço dos combustíveis;
- combustível utilizado.

### 4.3 Interface
- interface compacta com dados principais;
- interface expandida com detalhes adicionais;
- experiência simples, rápida e com foco em decisão imediata.

## 5. Regras de negócio

- o valor por quilômetro é calculado com base na distância total e no valor da corrida;
- a classificação visual deve refletir a percepção de rentabilidade;
- os dados de configuração influenciam o cálculo estimado de custos;
- a experiência deve priorizar clareza e agilidade.

## 6. Critérios de aceitação

O MVP será considerado adequado quando:
- o usuário consegue visualizar rapidamente os dados principais da corrida;
- o sistema apresenta uma análise objetiva de rentabilidade;
- a configuração do veículo e do combustível afeta corretamente os cálculos;
- a interface é simples de entender e usar.

## 7. Fluxo principal do usuário

1. o usuário visualiza uma corrida;
2. o sistema exibe os dados principais em interface compacta;
3. o usuário abre a visão expandida para mais detalhes;
4. o sistema apresenta a análise de rentabilidade;
5. o usuário decide aceitar ou não com base nessas informações.

## 8. Próximos passos

- consolidar a lógica de cálculo no app Android;
- melhorar a experiência visual;
- integrar persistência de configurações;
- evoluir para leitura de notificações e overlay.
