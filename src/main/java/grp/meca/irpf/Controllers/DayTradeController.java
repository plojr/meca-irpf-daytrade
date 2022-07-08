package grp.meca.irpf.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import grp.meca.irpf.Models.DayTrade;
import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Repositories.DayTradeRepository;
import grp.meca.irpf.Repositories.NotaDeCorretagemRepository;
import grp.meca.irpf.Repositories.OrdemRepository;
import grp.meca.irpf.Services.DayTradeService;
import grp.meca.irpf.Services.DayTradeServiceImpl;

@Controller
public class DayTradeController {
	
	@Autowired
	private DayTradeRepository dayTradeRepository;
	
	@Autowired
	private OrdemRepository ordemRepository;
	
	@Autowired
	private NotaDeCorretagemRepository corretagemRepository;

	@GetMapping("gerar_daytrades")
	public String addDayTradeBD() {
		List<NotaDeCorretagem> corretagens = corretagemRepository.findAllByOrderByDataAsc();
		corretagens.forEach(nc -> nc.setOrdens(ordemRepository.findByNotaDeCorretagem(nc)));
		dayTradeRepository.deleteAll();
		DayTradeService dtService = new DayTradeServiceImpl();
		List<DayTrade> dayTrades = dtService.getDayTrades(corretagens);
		for(DayTrade dayTrade: dayTrades) {
			System.out.println(dayTrade);
			dayTradeRepository.save(dayTrade);
		}
		return "redirect:/mostrar_daytrades";
	}
	
	@GetMapping("mostrar_daytrades")
	public String lerDayTradeBD(Model model) {
		model.addAttribute("dayTrades", dayTradeRepository.findAll());
		return "daytrade";
	}
	
	@GetMapping("gerar_relatorio_daytrade")
	public String gerarRelatorioDayTrade() {
		
		return "relatorio";
	}
}
