package grp.meca.irpf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import grp.meca.irpf.Models.DayTrade;
import grp.meca.irpf.Models.Relatorio;
import grp.meca.irpf.Models.Ticker;
import grp.meca.irpf.Services.RelatorioDayTradeServiceImpl;

@SpringBootTest
public class Testes01 {
	
	private static final double EPSILON = 0.001;

	/*
	 * Negociação que envolve menos de R$ 1 milhão em volume.
	 */
	@Test
	public void negociacaoMenorQue1Milhao() {
		DayTrade dt1 = new DayTrade(new Ticker("abcd3"), 1000, 10, 25, LocalDate.of(2022, 6, 20));
		DayTrade dt2 = new DayTrade(new Ticker("xpto3"), 1, 10, 10, LocalDate.of(2022, 7, 20));
		List<DayTrade> dayTrades = new ArrayList<>();
		dayTrades.add(dt1);
		dayTrades.add(dt2);
		RelatorioDayTradeServiceImpl relatorioDTService = new RelatorioDayTradeServiceImpl();
		Relatorio relatorio = relatorioDTService.gerarRelatorioParaTeste(dayTrades);
		Assertions.assertEquals(14991.95, relatorio.getAnoMesLucro().get(2022).get(6), EPSILON);
		Assertions.assertEquals(0., relatorio.getAnoMesPrejuizoAcumulado().get(2022).get(6), EPSILON);
		Assertions.assertEquals(2998.39, relatorio.getAnoMesImpostoDeRenda().get(2022).get(6), EPSILON);
		Assertions.assertEquals(-0.0046, relatorio.getAnoMesLucro().get(2022).get(7), EPSILON);
		Assertions.assertEquals(0.0046, relatorio.getAnoMesPrejuizoAcumulado().get(2022).get(7), EPSILON);
		Assertions.assertEquals(0., relatorio.getAnoMesImpostoDeRenda().get(2022).get(7), EPSILON);
	}
	
	/*
	 * Negociação que envolve menos de R$ 40 milhões em volume.
	 */
	@Test
	public void negociacaoMenorQue40Milhoes() {
		DayTrade dt1 = new DayTrade(new Ticker("abcd3"), 1000000, 10, 25, LocalDate.of(2022, 6, 20));
		List<DayTrade> dayTrades = new ArrayList<>();
		dayTrades.add(dt1);
		RelatorioDayTradeServiceImpl relatorioDTService = new RelatorioDayTradeServiceImpl();
		Relatorio relatorio = relatorioDTService.gerarRelatorioParaTeste(dayTrades);
		Assertions.assertEquals(14993000., relatorio.getAnoMesLucro().get(2022).get(6), EPSILON);
		Assertions.assertEquals(0., relatorio.getAnoMesPrejuizoAcumulado().get(2022).get(6), EPSILON);
		Assertions.assertEquals(2998600., relatorio.getAnoMesImpostoDeRenda().get(2022).get(6), EPSILON);
	}
	
	/*
	 * Negociação que envolve mais de R$ 4 bilhões em volume.
	 */
	@Test
	public void negociacaoMaisDe4Bilhoes() {
		DayTrade dt1 = new DayTrade(new Ticker("abcd3"), 1000000000, 10, 25, LocalDate.of(2022, 6, 20));
		List<DayTrade> dayTrades = new ArrayList<>();
		dayTrades.add(dt1);
		RelatorioDayTradeServiceImpl relatorioDTService = new RelatorioDayTradeServiceImpl();
		Relatorio relatorio = relatorioDTService.gerarRelatorioParaTeste(dayTrades);
		Assertions.assertEquals(14996150000., relatorio.getAnoMesLucro().get(2022).get(6), EPSILON);
		Assertions.assertEquals(0., relatorio.getAnoMesPrejuizoAcumulado().get(2022).get(6), EPSILON);
		Assertions.assertEquals(2999230000., relatorio.getAnoMesImpostoDeRenda().get(2022).get(6), EPSILON);
	}
}
