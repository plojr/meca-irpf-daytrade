/*
 * Esta classe é a parte mais básica. Cada compra ou venda feita é gerada um registro no banco relativo à esta classe.
 * Uma ordem é do tipo "compra de 100 itsa4 ao preço de R$ 10.00" ou "venda de 100 petr3 ao preço de R$ 30.00".
 * A variável tipo será 'c' para compra ou 'v' para venda.
 */

package grp.meca.irpf.Models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity(name = "ordens")
public class Ordem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private char tipo;
	
	@Column(nullable = false)
	private int quantidade;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticker_id", nullable = false)
	private Ticker ticker;
	
	@Column(nullable = false)
	private double preco;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "corretagem_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private NotaDeCorretagem notaDeCorretagem;
	
	/*
	 *  As taxas não precisam ser guardadas, já que elas podem ser calculadas a partir do 
	 *  valor bruto da nota de corretagem e as outras ordens.
	 *  Um exemplo de como as taxas podem ser calculadas, de acordo com a Receita Federal:
	 *  Suponha uma nota de corretagem de valor líquido (já com as taxas embutidas) de 
	 *  -4002.00 (o valor negativo indica que houve mais compras do que vendas). Agora 
	 *  suponha que exista uma compra de 100 ações aaaa3 por 1000.00 no total e 300 ações bbbb3 
	 *  por 3000.00 no total.
	 *  Com esses dados, você percebe que há um custo de 4000.00 com as ações, porém a nota 
	 *  de corretagem deduz de vocÊ 2.00 a mais. Estes 2.00 são de taxas. 
	 *  Como saber quanto, destes 2.00, são relativos à aaaa3 e quanto são relativos a bbbb3?
	 *  Nota-se que 25% do custo da nota vêm de aaaa3 e 75% vêm de bbbb3. Com isso, 25% da 
	 *  taxa podem ser atribuídas a aaaa3, 
	 *  enquanto 75% são de bbbb3. Com isso, o custo total de aaaa3 será 0.50 (25% da taxa 
	 *  de 2.00) + 1000.00, enquanto o custo de bbbb3 será 1.50 (75% da taxa de 2.00) + 3000.00.
	 *  
	 *  A instrução normativa de que se trata o texto acima é a Instrução Normativa RFB no 1.585, 
	 *  de 31 de agosto de 2015, art. 56, § 4o.
	 *  
	 *  Estas taxas podem ser acrescidas ao custo total na compra e abatidas na venda, tendo um 
	 *  ganho fiscal na hora do cálculo do ganho líquido.
	 *  
	 *  A lei, regulamento, decreto e instrução normativa onde possível ver isso:
	 *  (Lei no 8.383, de 30 de dezembro de 1991, art. 27; e Regulamento do Imposto sobre a Renda -
     *  RIR/2018, art. 841, § 2o, aprovado pelo Decreto no 9.580, de 22 de novembro de 2018; e Instrução
	 *  Normativa RFB no 1.585, de 31 de agosto de 2015, art. 56, § 3o)
	 */
	@Transient
	private double taxas;
	
	public Ordem() {}
	
	public Ordem(char tipo, int quantidade, Ticker ticker, double preco, NotaDeCorretagem notaDeCorretagem) throws Exception {
		if(tipo != 'c' && tipo != 'v') throw new Exception("Tipo de ordem inválido: " + tipo);
		this.tipo = tipo;
		if(quantidade <= 0) throw new Exception("Quantidade de ordem inválida: " + quantidade);
		this.quantidade = quantidade;
		this.ticker = ticker;
		if(preco <= 0) throw new Exception("Preço de ordem inválido: " + preco);
		this.preco = preco;
		this.notaDeCorretagem = notaDeCorretagem;
	}
	
}
