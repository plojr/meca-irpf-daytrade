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
	 * Sem este construtor, não foi possível usar dayTradeRepository em DayTradeController.
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
}
