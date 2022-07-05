/*
 * Ás vezes, a empresa quer aumentar seu capital social. Então ela propõe
 * uma bonificação. Com isso, o investidor "ganha" uma porcentagem de ações.
 * É possível adicionar um custo a este ganho para que seu preço médio aumente,
 * fazendo que o lucro contábil seja menor e, com isso, pague menos imposto de renda.
 * Na prática, o investidor não tem que desembolsar nenhum dinheiro. Porém, em teoria,
 * as ações que ele ganha têm um custo contábil. Este custo deve ser declarado.
 * Um exemplo de bonificação foi a do Bradesco em 2021, onde  o investidor "ganhou"
 * 10% da quantidade de ações que possuía a um custo contábil de R$ 4,128165265 por ação.
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

@EqualsAndHashCode(callSuper=false)
@Data
@Entity
public class Bonificacao extends EventoExtraordinario {
	
	public Bonificacao() {}

	public Bonificacao(Ticker ticker1, LocalDate dataEvento, double proporcao, double preco) {
		super(ticker1, dataEvento);
		this.proporcao = proporcao;
		this.preco = preco;
	}

	@Column(nullable = false)
	private double proporcao;
	
	@Column(nullable = false)
	private double preco;

	/*
	 * As ações que vêm da bonificação têm um custo contábil, isto é, apesar de você
	 * não ter que desembolsar nenhum real pelas ações, há um custo que você pode, e deve, 
	 * adicionar ao seu custo total.
	 * Algumas pessoas podem achar isso ruim, pois vai aumentar, pelo menos em teoria,
	 * seu preço médio por ação, porém isso te dá, legalmente, um benefício fiscal, pois,
	 * com o aumento do preço médio por ação, na hora da venda, seu lucro cairá, em termos
	 * contábeis, e não na prática. O imposto de renda é calculado tendo como base este 
	 * lucro contábil.
	 * 
	 * Exemplo: suponha que você tenha 1000 ações bbdc3 a um custo total de R$ 15000.00.
	 * Suponha também que ocorrerá uma bonificação de 10% a um custo, por ação, de $ 5.00.
	 * Com o evento, serão adicionadas 100 ações (10% de 1000) a um custo contábil de 
	 * R$ 500.00 (100 x R$ 5.00). Seu custo total irá para R$ 15500.00 e você passará a ter
	 * 1100 ações, isto é, um preço médio por ação de, aproximadamente, R$ 14.09.
	 * Se não houvesse este custo por ação do evento, então você teria 1100 ações a um custo
	 * total de R$ 15000.00, o que resultaria em um preço médio de, aproximadamente, R$ 13.64.
	 * Perceba que, com o custo relativo à bonificação, seu preço médio sobe. Na hora da venda,
	 * você pagará menos imposto de renda, já que seu lucro vai ser menor em relação a uma 
	 * hipotética bonificação sem custo.
	 * 
	 * Só para reforçar: tudo isso está dentro da lei.
	 */
	public void aplicarEvento(Map<String, Pair<Integer, Double>> carteira) {
		if(carteira == null) return;
		String ticker = this.getTicker1().getCodigo();
		if(!carteira.containsKey(ticker)) return;
		int quantidadeBonificacao = (int)(carteira.get(ticker).getFirst()*proporcao);
		double custoBonificacao = quantidadeBonificacao*preco;
		double novoCustoTotal = carteira.get(ticker).getSecond() + custoBonificacao;
		int novaQuantidade = carteira.get(ticker).getFirst() + quantidadeBonificacao;
		carteira.put(ticker, Pair.of(novaQuantidade, novoCustoTotal));
	}
}
