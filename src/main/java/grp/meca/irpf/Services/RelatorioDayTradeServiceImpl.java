package grp.meca.irpf.Services;

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
		dayTrades.forEach(dayTrade -> 
			MapUtil.add(anoMesLucro, dayTrade.getData().getYear(), dayTrade.getData().getMonthValue(), dayTrade.calculaLucro())
		);
		return anoMesLucro;
	}
	
}
