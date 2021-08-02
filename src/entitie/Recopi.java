package entitie;

import java.io.Serializable;
import java.util.Date;

public class Recopi implements Serializable {

	private String controle;
	private String tipo;
	private String cnpjOrigem;
	private String razaoOrigem;
//	private String ufOrigem;
	private String cnpjDestino;
	private String razaoDestino;
//	private String ufDestino;
	private String tipoOperacao;
	private String formaRegistro;
	private String situacao;
	private int dias;
	private String cfop;
	private String descricao;
	private String notaFiscal;
	private String serie;
	private Date dataEmissao;
	private Date dataSaida;
	private Date dataEntrada;
	private double valor;

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

	public String getCnpjOrigem() {
		return cnpjOrigem;
	}

	public void setCnpjOrigem(String cnpjOrigem) {
		this.cnpjOrigem = cnpjOrigem;
	}

	public String getRazaoOrigem() {
		return razaoOrigem;
	}

	public void setRazaoOrigem(String razaoOrigem) {
		this.razaoOrigem = razaoOrigem;
	}

//	public String getUfOrigem() {
//		return ufOrigem;
//	}
//
//	public void setUfOrigem(String ufOrigem) {
//		this.ufOrigem = ufOrigem;
//	}

	public String getCnpjDestino() {
		return cnpjDestino;
	}

	public void setCnpjDestino(String cnpjDestino) {
		this.cnpjDestino = cnpjDestino;
	}

	public String getRazaoDestino() {
		return razaoDestino;
	}

	public void setRazaoDestino(String razaoDestino) {
		this.razaoDestino = razaoDestino;
	}

//	public String getUfDestino() {
//		return ufDestino;
//	}
//
//	public void setUfDestino(String ufDestino) {
//		this.ufDestino = ufDestino;
//	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public String getFormaRegistro() {
		return formaRegistro;
	}

	public void setFormaRegistro(String formaRegistro) {
		this.formaRegistro = formaRegistro;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public String getCfop() {
		return cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getOrdem() {
		return getControle() + "_" + getTipo() + "_" + getNotaFiscal();
	}
}
