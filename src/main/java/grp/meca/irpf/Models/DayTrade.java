package grp.meca.irpf.Models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity(name = "day_trade")
public class DayTrade {
	
	/*
	 * Sem este construtor, não é possível usar dayTradeRepository em DayTradeController.
	 */
	public DayTrade() {}
	
	public DayTrade(Ticker ticker, int quantidade, double precoCompra, double precoVenda, LocalDate data) {
		super();
		this.ticker = ticker;
		this.quantidade = quantidade;
		this.precoCompra = precoCompra;
		this.precoVenda = precoVenda;
		this.data = data;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "ticker_id", nullable = false)
	private Ticker ticker;
	
	@Column(nullable = false)
	private int quantidade;
	
	@Column(nullable = false)
	private double precoCompra;
	
	@Column(nullable = false)
	private double precoVenda;
	
	@Column(nullable = false)
	private LocalDate data;
	
	public double getLucro() {
		return (precoVenda - precoCompra)*quantidade;
	}
	
	public double getNegociacao() {
		return (precoVenda + precoCompra)*quantidade;
	}
	
	/*
	 * Tabela retirada do site, acessado em 10/07/2022, 
	 * https://www.b3.com.br/pt_br/produtos-e-servicos/tarifas/listados-a-vista-e-derivativos/renda-variavel/tarifas-de-acoes-e-fundos-de-investimento/a-vista/
	 */
	public static double calculaTaxas(double negociacaoDiaria) {
		if(negociacaoDiaria <= 1000000.)
			return 0.00023*negociacaoDiaria;
		if(negociacaoDiaria <= 5000000.)
			return 0.000225*negociacaoDiaria;
		if(negociacaoDiaria <= 10000000.)
			return 0.00021*negociacaoDiaria;
		if(negociacaoDiaria <= 40000000.)
			return 0.0002*negociacaoDiaria;
		if(negociacaoDiaria <= 150000000.)
			return 0.000185*negociacaoDiaria;
		if(negociacaoDiaria <= 300000000.)
			return 0.000175*negociacaoDiaria;
		if(negociacaoDiaria <= 700000000.)
			return 0.00016*negociacaoDiaria;
		if(negociacaoDiaria <= 1000000000.)
			return 0.000145*negociacaoDiaria;
		if(negociacaoDiaria <= 2000000000.)
			return 0.000135*negociacaoDiaria;
		if(negociacaoDiaria <= 3000000000.)
			return 0.000125*negociacaoDiaria;
		if(negociacaoDiaria <= 4000000000.)
			return 0.00012*negociacaoDiaria;
		return 0.00011*negociacaoDiaria;
	}
	
	public static double getTaxasIR() {
		return 0.20;
	}
}
