package grp.meca.irpf.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import grp.meca.irpf.Services.RelatorioDayTradeServiceImpl;

@Controller
public class RelatorioDayTradeController {

	@Autowired
	private RelatorioDayTradeServiceImpl relatorioService;
	
	@GetMapping("gerar_relatorio_daytrade")
	public String gerarRelatorioDayTrade(Model model) {
		model.addAttribute("relatorio", relatorioService.gerarRelatorio());
		return "relatorio";
	}
}
