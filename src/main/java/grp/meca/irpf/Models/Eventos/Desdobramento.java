/*
 * Desobramento é um evento onde uma empresa decide multiplicar suas ações em N vezes para
 * aumentar a quantidade de ações da empresa. Quando a ação está em um valor
 * nominal muito alto, é muito comum ter desdobramentos para que a ação tenha mais liquidez.
 * Um exemplo de desdobramento foi a Copel em 2021.
 * Para cada ação de Copel, qualquer que fosse a classe, que o investidor possuía, 
 * ele passou a possuir 10.
 * Obviamente o preço por ação é dividido por 10.
 * No caso da variável "proporção", para o exemplo acima, o valor será 10.
 */

package grp.meca.irpf.Models.Eventos;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.Basico.Ticker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper=false)
@ToString(callSuper=true)
@Data
@Entity
public class Desdobramento extends EventoExtraordinario {
	
	public Desdobramento() {}
	
	public Desdobramento(Ticker ticker1, LocalDate dataEvento, double proporcao) {
		super(ticker1, dataEvento);
		this.proporcao = proporcao;
	}

	@Column(nullable = false)
	private double proporcao;
	
	public void aplicarEvento(Map<String, Pair<Integer, Double>> carteira) {
		if(carteira == null) return;
		String ticker = this.getTicker1().getCodigo();
		if(!carteira.containsKey(ticker)) return;
		int novaQuantidade = (int)(carteira.get(ticker).getFirst()*proporcao);
		carteira.put(ticker, Pair.of(novaQuantidade, carteira.get(ticker).getSecond()));
	}
}
