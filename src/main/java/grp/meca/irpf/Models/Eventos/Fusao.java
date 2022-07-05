package grp.meca.irpf.Models.Eventos;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.Basico.Ticker;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
@Entity
public class Fusao extends EventoExtraordinario {

	public Fusao() {}
	
	public Fusao(Ticker ticker1, LocalDate dataEvento, Ticker ticker2) {
		super(ticker1, dataEvento);
		this.ticker2 = ticker2;
	}
	
	// Esta é a empresa com a qual haverá a fusão.
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticker_id_2", nullable = false)
	private Ticker ticker2;
	
	@Column(nullable = false)
	private double preco;
	
	@Column(nullable = false)
	private double proporcaoDeAcoes;
	
	//TODO
	public void aplicarEvento(Map<String, Pair<Integer, Double>> carteira) {
		
	}
}
