package grp.meca.irpf.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.DayTrade;
import grp.meca.irpf.Models.NotaDeCorretagem;
import grp.meca.irpf.Models.Ordem;
import grp.meca.irpf.Models.Ticker;

public class DayTradeServiceImpl implements DayTradeService {

	@Override
	public List<DayTrade> getDayTrades(List<NotaDeCorretagem> corretagens) {
		List<DayTrade> dayTrades = new ArrayList<>();
		corretagens.forEach(corretagem -> {
			/*
			 * Uma nota de corretagem pode conter várias ordens do mesmo ticker.
			 * Para simplificar, as ordens de compra foram consolidadas na variável notaConsolidadaCompras
			 * e as ordens de venda, em notaConsolidadaVendas.
			 * Isso vai facilitar para separar as ordens relativas ao day trade.
			 */
			Map<String, Pair<Integer, Double>> notaConsolidadaCompras = new HashMap<>(), notaConsolidadaVendas = new HashMap<>();
			// Set para guardar os tickers de forma única e poder acessá-los posteriormente.
			Set<String> tickers = new LinkedHashSet<>();
			atualizarNotasDeCorretagem(notaConsolidadaCompras, notaConsolidadaVendas, tickers, corretagem);
			for(String ticker: tickers) {
				List<Ordem> ordensDayTrade = getOrdensDayTradeByTicker(ticker, notaConsolidadaCompras, notaConsolidadaVendas);
				if(ordensDayTrade.size() != 0) {
					
				}
			}
		});
		return dayTrades;
	}
	
	private void atualizarNotasDeCorretagem(Map<String, Pair<Integer, Double>> notaConsolidadaCompras,
			Map<String, Pair<Integer, Double>> notaConsolidadaVendas, Set<String> tickers,
			NotaDeCorretagem corretagem) {
		for(Ordem ordem: corretagem.getOrdens()) {
			String ticker = ordem.getTicker().getCodigo();
			tickers.add(ticker);
			if(ordem.getTipo() == 'c')
				atualizarNotaConsolidada(notaConsolidadaCompras, ticker, ordem);
			else
				atualizarNotaConsolidada(notaConsolidadaVendas, ticker, ordem);
		}
	}
	
	/*
	 * Uma nota consolidada, variável encontrada dentro da função, é uma onde todas as negociações 
	 * do mesmo tipo de um ticker são agrupadas em um único item do Map.
	 * Por exemplo: se houver 3 ordens de compra de xpto, então será consolidada uma única 
	 * ordem de compra de xpto com o preço médio ponderado e a quantidade igual à soma da quantidade 
	 * das três ordens.
	 */
	private void atualizarNotaConsolidada(Map<String, Pair<Integer, Double>> notaConsolidada, String ticker,
			Ordem ordem) {
		if(notaConsolidada.containsKey(ticker)) {
			int quantidadeAtual = notaConsolidada.get(ticker).getFirst();
			double custoAtual = notaConsolidada.get(ticker).getSecond();
			notaConsolidada.put(ticker, Pair.of(ordem.getQuantidade() + quantidadeAtual, ordem.getPreco()*ordem.getQuantidade() + custoAtual));
		}
		else
			notaConsolidada.put(ticker, Pair.of(ordem.getQuantidade(), ordem.getPreco()*ordem.getQuantidade()));
	}
	
	private List<Ordem> getOrdensDayTradeByTicker(String ticker,
			Map<String, Pair<Integer, Double>> notaConsolidadaCompras,
			Map<String, Pair<Integer, Double>> notaConsolidadaVendas) {
		List<Ordem> ordens = new ArrayList<>();
		if(notaConsolidadaCompras.containsKey(ticker) && notaConsolidadaVendas.containsKey(ticker)) {
			int quantidade = Math.min(notaConsolidadaCompras.get(ticker).getFirst(), notaConsolidadaVendas.get(ticker).getFirst());
			double precoMedioCompra = notaConsolidadaCompras.get(ticker).getSecond()/notaConsolidadaCompras.get(ticker).getFirst();
			double precoMedioVenda = notaConsolidadaVendas.get(ticker).getSecond()/notaConsolidadaVendas.get(ticker).getFirst();
			try {
				Ordem ordemVenda = new Ordem('v', quantidade, new Ticker(ticker), precoMedioVenda, null);
				Ordem ordemCompra = new Ordem('c', quantidade, new Ticker(ticker), precoMedioCompra, null);
				ordens.add(ordemCompra);
				ordens.add(ordemVenda);
			} catch(Exception e) {
				System.err.println("Erro em DayTradeService.getOrdensDayTradeByTicker, com a seguinte mensagem: " + e.getMessage());
			}
		}
		return ordens;
	}
}
