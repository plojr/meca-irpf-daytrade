# MeCa-IRPF-DayTrade

Status: em andamento.

## Resumo

Este módulo é responsável por calcular os day trades existentes a partir do módulo cadastro. 

## Funcionalidades

A funcionalidade "Cálculo day trade" pega os dados do cadastro e faz os cálculos de day trade, incluindo o imposto de renda a ser pago naquele mês do day trade, os lucros, prejuízos e quanto o investidor tem, se houver, de prejuízo acumulado. Após o cálculo, dados são inseridos no banco de dados.

A funcionalidade "Ler dados day trade" pega os dados das tabelas de day trade para mostrar na tela. Não há recálculo dos dados. Para tal, é necessário usar a funcionalidade anterior.

## Objetivo

O intuito deste módulo é permitir que o investidor saiba quanto ele precisa pagar no mês decorrente dos day trade que ele fez. Além disso, ele consegue saber quanto de prejuízo acumulado ele, eventualmente, possui.
