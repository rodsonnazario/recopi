package entitie;

import java.io.Serializable;

public class RecopiItem implements Serializable {

	private String controle;
	private String tipo;
	private String ncm;
	private String tipoPapel;
	private String notaFiscal;
	private double quantidade;
	private double quantidadeDevolvida;
	private double quantidadeAceita;
	private double quantidadeAReceber;
	private double quantidadeRecebida;
	private double perda;
	private double sinistro;

	public String getControle() {
		return controle;
	}

	public void setControle(String controle) {
		this.controle = controle;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public String getTipoPapel() {
		return tipoPapel;
	}

	public void setTipoPapel(String tipoPapel) {
		this.tipoPapel = tipoPapel;
	}

	public String getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public double getQuantidadeDevolvida() {
		return quantidadeDevolvida;
	}

	public void setQuantidadeDevolvida(double quantidadeDevolvida) {
		this.quantidadeDevolvida = quantidadeDevolvida;
	}

	public double getQuantidadeAceita() {
		return quantidadeAceita;
	}

	public void setQuantidadeAceita(double quantidadeAceita) {
		this.quantidadeAceita = quantidadeAceita;
	}

	public double getQuantidadeAReceber() {
		return quantidadeAReceber;
	}

	public void setQuantidadeAReceber(double quantidadeAReceber) {
		this.quantidadeAReceber = quantidadeAReceber;
	}

	public double getQuantidadeRecebida() {
		return quantidadeRecebida;
	}

	public void setQuantidadeRecebida(double quantidadeRecebida) {
		this.quantidadeRecebida = quantidadeRecebida;
	}

	public double getPerda() {
		return perda;
	}

	public void setPerda(double perda) {
		this.perda = perda;
	}

	public double getSinistro() {
		return sinistro;
	}

	public void setSinistro(double sinistro) {
		this.sinistro = sinistro;
	}

	public String getOrdem() {
		return getControle() + "_" + getNcm() + "_" + getTipo() + "_" + getNotaFiscal();
	}
}
