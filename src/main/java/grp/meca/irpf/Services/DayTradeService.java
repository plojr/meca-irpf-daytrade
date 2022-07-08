package grp.meca.irpf.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import grp.meca.irpf.Models.DayTrade;
import grp.meca.irpf.Models.NotaDeCorretagem;

@Service
public interface DayTradeService {

	public List<DayTrade> getDayTrades(List<NotaDeCorretagem> corretagens);
}
