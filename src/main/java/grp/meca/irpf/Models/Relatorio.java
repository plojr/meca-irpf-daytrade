/*
 * Um relatório será composto de três variáveis correspondendo ao mesmo tipo:
 * um mapeamento de ano e mês para um valor. Este valor serão, em suas respectivas
 * variáveis, o lucro/prejuízo mensal, o prejuízo acumulado até aquele mês e o
 * imposto de renda que deve ser pago naquele mês.
 * Para exemplificar:
 * "anoMesLucro.get(2022).get(3)" significa o lucro/prejuízo (a depender do sinal
 * do valor) do mês de março no ano de 2022.
 */
package grp.meca.irpf.Models;

import java.util.Map;

import lombok.Data;

@Data
public class Relatorio {

	private Map<Integer, Map<Integer, Double>> anoMesLucro;
	private Map<Integer, Map<Integer, Double>> anoMesPrejuizoAcumulado;
	private Map<Integer, Map<Integer, Double>> anoMesImpostoDeRenda;
}
