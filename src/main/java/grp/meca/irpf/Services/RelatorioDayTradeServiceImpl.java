package grp.meca.irpf.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import grp.meca.irpf.Models.DayTrade;
import grp.meca.irpf.Models.Relatorio;
import grp.meca.irpf.Repositories.DayTradeRepository;
import grp.meca.irpf.Utils.MapUtil;

@Service
public class RelatorioDayTradeServiceImpl implements RelatorioDayTradeService {
	
	@Autowired
	private DayTradeRepository dayTradeRepository;
	
	@Override
	public Relatorio gerarRelatorio() {
		Relatorio relatorio = new Relatorio();
		List<DayTrade> dayTrades = dayTradeRepository.findAll();
		relatorio.setAnoMesLucro(calculaLucroMensal(dayTrades));
		relatorio.setAnoMesPrejuizoAcumulado(calculaPrejuizoAcumulado(relatorio.getAnoMesLucro()));
		relatorio.setAnoMesImpostoDeRenda(calculaImpostoDeRenda(relatorio.getAnoMesLucro(), relatorio.getAnoMesPrejuizoAcumulado()));
		return relatorio;
	}
	
	public Relatorio gerarRelatorioParaTeste(List<DayTrade> dayTrades) {
		Relatorio relatorio = new Relatorio();
		relatorio.setAnoMesLucro(calculaLucroMensal(dayTrades));
		relatorio.setAnoMesPrejuizoAcumulado(calculaPrejuizoAcumulado(relatorio.getAnoMesLucro()));
		relatorio.setAnoMesImpostoDeRenda(calculaImpostoDeRenda(relatorio.getAnoMesLucro(), relatorio.getAnoMesPrejuizoAcumulado()));
		return relatorio;
	}

	private Map<Integer, Map<Integer, Double>> calculaImpostoDeRenda(Map<Integer, Map<Integer, Double>> anoMesLucro,
			Map<Integer, Map<Integer, Double>> anoMesPrejuizoAcumulado) {
		Map<Integer, Map<Integer, Double>> anoMesImpostoDeRenda = new TreeMap<>();
		double prejuizoAcumuladoCorrente = 0;
		for(Entry<Integer, Map<Integer, Double>> entryAnoMesLucro: anoMesLucro.entrySet()) {
			int ano = entryAnoMesLucro.getKey();
			for(Entry<Integer, Double> entryMesLucro: entryAnoMesLucro.getValue().entrySet()) {
				int mes = entryMesLucro.getKey();
				double lucro = entryMesLucro.getValue();
				double impostoDeRenda = Math.max(0, DayTrade.getTaxasIR()*(lucro - prejuizoAcumuladoCorrente));
				MapUtil.put(anoMesImpostoDeRenda, ano, mes, impostoDeRenda);
				prejuizoAcumuladoCorrente = anoMesPrejuizoAcumulado.get(ano).get(mes);
			}
		}
		return anoMesImpostoDeRenda;
	}

	private Map<Integer, Map<Integer, Double>> calculaPrejuizoAcumulado(Map<Integer, Map<Integer, Double>> anoMesLucro) {
		double prejuizoAcumulado = 0;
		Map<Integer, Map<Integer, Double>> anoMesPrejuizoAcumulado = new TreeMap<>();
		for(Entry<Integer, Map<Integer, Double>> entryAnoMesLucro: anoMesLucro.entrySet()) {
			int ano = entryAnoMesLucro.getKey();
			for(Entry<Integer, Double> entryMesLucro: entryAnoMesLucro.getValue().entrySet()) {
				int mes = entryMesLucro.getKey();
				double lucro = entryMesLucro.getValue();
				if(lucro < 0)
					prejuizoAcumulado += Math.abs(lucro);
				else {
					if(prejuizoAcumulado > lucro)
						prejuizoAcumulado -= lucro;
					else
						prejuizoAcumulado = 0;
				}
				MapUtil.add(anoMesPrejuizoAcumulado, ano, mes, prejuizoAcumulado);
			}
		}
		return anoMesPrejuizoAcumulado;
	}

	private Map<Integer, Map<Integer, Double>> calculaLucroMensal(List<DayTrade> dayTrades) {
		Map<Integer, Map<Integer, Double>> anoMesLucro = new TreeMap<>();
		Map<LocalDate, Double> negociacaoDiaria = new TreeMap<>();
		/*
		 * Primeiro, ?? preciso calcular o lucro bruto de cada day trade.
		 * Al??m disso, ?? preciso tamb??m calcular a negociacao di??ria, de forma acumulada, que ?? feito pelo MapUtil.add().
		 * Ap??s o forEach(), teremos toda a negocia????o di??ria para podermos calcular as taxas daquele dia.
		 * Diante disso, ser?? poss??vel calcular quanto de taxa foi paga naquele m??s.
		 * Uma vez que as taxas dependem do volume de negocia????o - quanto maior o volume, menor a % das taxas -,
		 * ent??o ?? necess??rio saber o total de volume em um dia para poder saber qual % aplicar em cima do volume.
		 */
		dayTrades.forEach(dayTrade -> {
			MapUtil.add(anoMesLucro, dayTrade.getData().getYear(), dayTrade.getData().getMonthValue(), dayTrade.getLucro());
			MapUtil.add(negociacaoDiaria, dayTrade.getData(), dayTrade.getNegociacao());
		});
		/*
		 * Ap??s saber o quanto de negocia????o foi feita por dia, agora ?? poss??vel saber o quanto de taxa ser?? pago naquele dia.
		 * Com isso, ?? poss??vel abater este valor do lucro mensal relativo ?? data daquele day trade.
		 * Por fim, o lucro l??quido ?? calculado e atribu??do ?? vari??vel que mant??m o lucro mensal.
		 */
		for(Entry<LocalDate, Double> negociacao: negociacaoDiaria.entrySet()) {
			int mes = negociacao.getKey().getMonthValue();
			int ano = negociacao.getKey().getYear();
			double taxas = DayTrade.calculaTaxas(negociacao.getValue());
			double novoLucro = anoMesLucro.get(ano).get(mes) - taxas;
			anoMesLucro.get(ano).put(mes, novoLucro);
		}
		return anoMesLucro;
	}
	
}
