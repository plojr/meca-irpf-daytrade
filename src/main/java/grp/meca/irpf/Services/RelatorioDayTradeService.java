package grp.meca.irpf.Services;

import org.springframework.stereotype.Service;

import grp.meca.irpf.Models.Relatorio;

@Service
public interface RelatorioDayTradeService {
	public Relatorio gerarRelatorio();
}
