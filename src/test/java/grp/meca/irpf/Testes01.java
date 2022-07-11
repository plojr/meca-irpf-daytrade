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

	@Test
	public void teste01() {
		DayTrade dt1 = new DayTrade(new Ticker("abcd3"), 1000, 10, 25, LocalDate.of(2022, 6, 20));
		DayTrade dt2 = new DayTrade(new Ticker("xpto3"), 1, 10, 10, LocalDate.of(2022, 7, 20));
		List<DayTrade> dayTrades = new ArrayList<>();
		dayTrades.add(dt1);
		dayTrades.add(dt2);
		RelatorioDayTradeServiceImpl relatorioDTService = new RelatorioDayTradeServiceImpl();
		Relatorio relatorio = relatorioDTService.gerarRelatorioParaTeste(dayTrades);
		Assertions.assertEquals(14991.95, relatorio.getAnoMesLucro().get(2022).get(6), EPSILON);
		Assertions.assertEquals(0, relatorio.getAnoMesPrejuizoAcumulado().get(2022).get(6), EPSILON);
		Assertions.assertEquals(2998.39, relatorio.getAnoMesImpostoDeRenda().get(2022).get(6), EPSILON);
		Assertions.assertEquals(-0.0046, relatorio.getAnoMesLucro().get(2022).get(7), EPSILON);
		Assertions.assertEquals(0.0046, relatorio.getAnoMesPrejuizoAcumulado().get(2022).get(7), EPSILON);
		Assertions.assertEquals(0, relatorio.getAnoMesImpostoDeRenda().get(2022).get(7), EPSILON);
	}
}
