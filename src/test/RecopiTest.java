package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import program.Aplicacao;
import entitie.Recopi;
import entitie.RecopiConsulta;
import entitie.RecopiItem;

public class RecopiTest {
	private String diretorio;

	private ChromeOptions chromeOptions;
	private WebDriver driver;
	private JavascriptExecutor jsExecutor;
	
	private RecopiConsulta consulta;
	private List<Recopi> operacoes;
	private List<RecopiItem> operacoesItens;
	private List<RecopiItem> itens;
	private List<String> mensagens;
	private int quantidadeRegistro;
	private int linhaRegistro;
	private int linhaPapel;
	private int linhaNota;
	private boolean driverEncontrado;
	private boolean gravarArquivo;	
	
	private HSSFWorkbook xwb;
	String arquivoXLS = "recopi_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".xls";
	String arquivoLOG = "recopi_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".log";

	public RecopiTest(RecopiConsulta consulta) {
		this.consulta = consulta;
	}

	public RecopiConsulta getConsulta() {
		return consulta;
	}

	public void iniciar() {
		try {
			diretorio = System.getProperty("user.dir");
			mensagens = new ArrayList<String>();
			
			chromeOptions = new ChromeOptions();
			if(!consulta.isBrowser())
				chromeOptions.addArguments("--headless");
			driver = new ChromeDriver(chromeOptions);
			jsExecutor = (JavascriptExecutor) driver;
			
			operacoes = new ArrayList<Recopi>();
			operacoesItens = new ArrayList<RecopiItem>();
			itens = new ArrayList<RecopiItem>();
			driverEncontrado = true;
			gravarArquivo = true;			
			mensagem("Iniciando coleta de dados do Recopi.");
		} catch (IllegalStateException e) {
			mensagem("Feche o Aplicativo e tente novamente.");
			mensagem("Descompacte o arquivo que foi baixado no mesmo diretório do Aplicativo.");
			mensagem("https://sites.google.com/a/chromium.org/chromedriver/downloads");
			mensagem("Baixe o driver da versão do seu Google Chrome no link abaixo.");
			mensagem("Verifique a versão do seu navegador abrindo o link: (chrome://settings/help).");
			mensagem("Driver do Google Chrome não encontrado.");
			driverEncontrado = false;
		}
	}

	public void informarTelaLogin(RecopiConsulta consulta) {
		try {
			driver.findElement(By.id("UserName")).click();
			driver.findElement(By.id("UserName")).sendKeys(consulta.getUsuario());
			driver.findElement(By.id("Password")).click();
			driver.findElement(By.id("Password")).sendKeys(consulta.getSenha());
			driver.findElement(By.id("Login")).click();
			mensagem("Login realizado com sucesso.");
		} catch (Exception e) {
			mensagem("Tela de login não encontrada.");
			gravarArquivo = false;
		}
	}

	public void fecharTelaAviso() {
		try {
			driver.findElement(By.cssSelector("span.ui-button-text")).click();
			mensagem("Tela de aviso fechada.");
		} catch (Exception e) {
			mensagem("Tela de aviso não encontrada.");
		}
	}

	public void abrirTelaConsulta() {
		try {
			driver.findElement(By.linkText("Operações")).click();
			driver.findElement(By.linkText("Consultar Operações")).click();
			driver.findElement(By.id("txtNumeroRegistroControle")).clear();
		} catch (Exception e) {
			mensagem("Tela de consulta de operações não encontrada.");
			gravarArquivo = false;
		}
	}

	public void informarTelaConsulta(RecopiConsulta consulta) {
		try {
			// entrada de controle de recopi
			if (consulta.getControle() != null && !consulta.getControle().isEmpty()) {
				driver.findElement(By.id("txtNumeroRegistroControle")).click();
				driver.findElement(By.id("txtNumeroRegistroControle")).sendKeys(consulta.getControle());
			} else {
				// entrada de período
				if (consulta.getDataInicial() != null && !consulta.getDataInicial().toString().isEmpty()) {
					driver.findElement(By.id("txtDataInicial")).click();
					driver.findElement(By.id("txtDataInicial"))
							.sendKeys(new SimpleDateFormat("dd/MM/yyyy").format(consulta.getDataInicial()));
					driver.findElement(By.id("txtDataFinal")).click();
					driver.findElement(By.id("txtDataFinal"))
							.sendKeys(new SimpleDateFormat("dd/MM/yyyy").format(consulta.getDataFinal()));
				}
				// entrada de referência mm/yyyy
				if (consulta.getMesAno() != null && !consulta.getMesAno().toString().isEmpty()) {
					driver.findElement(By.id("txtMesAno")).click();
					driver.findElement(By.id("txtMesAno"))
							.sendKeys(new SimpleDateFormat("MM/yyyy").format(consulta.getMesAno()));
				}
				// entrada de situação da recopi
				if (consulta.getSituacao() != null && !consulta.getSituacao().isEmpty()) {
					driver.findElement(By.id("ddlSituacaoOperacao")).click();
					driver.findElement(By.xpath("//option[. = '" + consulta.getSituacao() + "']")).click();
					driver.findElement(By.id("ddlSituacaoOperacao")).click();
				}
				// entrada de tipo de operação
				if (consulta.getTipoOperacao() != null && !consulta.getTipoOperacao().isEmpty()) {
					driver.findElement(By.id("ddlTipoOperacao")).click();
					driver.findElement(By.xpath("//option[. = '" + consulta.getTipoOperacao() + "']")).click();
					driver.findElement(By.id("ddlTipoOperacao")).click();
				}
			}
			// consultar
			driver.findElement(By.id("btnConsultar")).click();
		} catch (Exception e) {
			mensagem("Ocorreu algum problema na tela de consulta de operações.");
			gravarArquivo = false;
		}
	}

	public void verificarRetornoTelaConsulta() {
		try {
			consulta.setMensagem(driver.findElement(By.id("spnErroMaster")).getText());
			if (consulta.getMensagem().isEmpty())
				consulta.setMensagem(driver.findElement(By.id("lblErroConsultaDadosOperacao")).getText());
			if (consulta.getMensagem().isEmpty())
				throw new Exception();
			else
				gravarArquivo = false;
		} catch (Exception e) {
			quantidadeRegistro = Integer
					.parseInt(driver.findElement(By.cssSelector("h2:nth-child(2)")).getText().replaceAll("[^0-9]", ""));
			consulta.setMensagem("Encontrado(s) " + quantidadeRegistro + " registro(s)");
		} finally {
			mensagem(consulta.getMensagem());
		}
	}

	public void gravarRecopi() {
		linhaRegistro = 0;
		while (quantidadeRegistro > 0) {
			// registro atual (a linha 1 refere-se ao cabeçalho da tabela)
			linhaRegistro++;
			if (linhaRegistro < 2) {
				jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				continue;
			}

			// controle de total de registros
			quantidadeRegistro--;

			// avança página a cada 15 registros [cabeçalho (1) + registros (15) + rodapé
			// (1)]
			if (linhaRegistro == 17) {
				linhaRegistro = 2;
				jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				driver.findElement(By.id("LinkButton3")).click();
			}

			// grava os nº de recopi encontrados
			try {
				mensagem("Encontrado Recopi "
						+ driver.findElement(
								By.xpath("//*[@id=\"grvOperacoes\"]/tbody/tr[" + linhaRegistro + "]/td[1]")).getText()
						+ ".");
				Recopi recopi = new Recopi();
				recopi.setTipo("REMESSA");
				recopi.setControle(
						driver.findElement(By.xpath("//*[@id=\"grvOperacoes\"]/tbody/tr[" + linhaRegistro + "]/td[1]"))
								.getText());
				recopi.setCnpjOrigem(
						driver.findElement(By.xpath("//*[@id=\"grvOperacoes\"]/tbody/tr[" + linhaRegistro + "]/td[2]"))
								.getText());
				recopi.setCnpjDestino(
						driver.findElement(By.xpath("//*[@id=\"grvOperacoes\"]/tbody/tr[" + linhaRegistro + "]/td[4]"))
								.getText());
				recopi.setFormaRegistro(
						driver.findElement(By.xpath("//*[@id=\"grvOperacoes\"]/tbody/tr[" + linhaRegistro + "]/td[7]"))
								.getText());
				recopi.setSituacao(
						driver.findElement(By.xpath("//*[@id=\"grvOperacoes\"]/tbody/tr[" + linhaRegistro + "]/td[9]"))
								.getText());
				recopi.setDias(Integer.parseInt(
						driver.findElement(By.xpath("//*[@id=\"grvOperacoes\"]/tbody/tr[" + linhaRegistro + "]/td[10]"))
								.getText()));

				operacoes.add(recopi);
			} catch (Exception e) {
				mensagem("Nenhum registro encontrado.");
				gravarArquivo = false;
			}
		}
	}

	private void gravarOperacoes() {
		quantidadeRegistro = operacoes.size();
		for (int i = 0; i < quantidadeRegistro; i++) {
			Recopi recopi = operacoes.get(i);

			mensagem("Consultando Recopi " + recopi.getControle() + ". (" + (i + 1) + " de " + quantidadeRegistro + ")");
			consulta = new RecopiConsulta();
			consulta.setControle(recopi.getControle());
			abrirTelaConsulta();
			informarTelaConsulta(consulta);

			driver.findElement(By.id("lkbVerDetalhe")).click();
			recopi.setTipoOperacao(driver.findElement(By.id("txtTipoOperacao")).getAttribute("value"));
			recopi.setCfop(driver.findElement(By.xpath("//*[@id=\"ConteudoPrincipal\"]/div/div[6]/div[1]")).getText());
			recopi.setDescricao(
					driver.findElement(By.xpath("//*[@id=\"ConteudoPrincipal\"]/div/div[6]/div[2]")).getText());
			recopi.setRazaoOrigem(
					driver.findElement(By.name("ctl00$ConteudoPagina$ctlDadosEstabelecimentoOrigem$txtRazaoSocial"))
							.getAttribute("value"));
			recopi.setRazaoDestino(
					driver.findElement(By.name("ctl00$ConteudoPagina$ctlDadosEstabelecimentoDestino$txtRazaoSocial"))
							.getAttribute("value"));

			jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");

			if (!recopi.getSituacao().equals("ABERTO") && !recopi.getSituacao().equals("CANCELADO")) {
				recopi.setNotaFiscal(driver.findElement(By.id("txtNumero")).getAttribute("value")
						.replace(" ", "").replace(".", ""));
				recopi.setSerie(driver.findElement(By.id("txtSerie")).getAttribute("value"));

				try {
					recopi.setDataEmissao(new SimpleDateFormat("dd/MM/yyyy")
							.parse(driver.findElement(By.id("txtDtEmissao")).getAttribute("value")));
				} catch (Exception e) {
					try {
						recopi.setDataEmissao(new SimpleDateFormat("dd/MM/yyyy")
								.parse(driver.findElement(By.id("txtDtEmissao")).getText()));
					} catch (Exception e2) {
						mensagem("Recopi " + recopi.getControle() + " não possui data emissão.");
					}
				}
				try {
					recopi.setDataSaida(new SimpleDateFormat("dd/MM/yyyy")
							.parse(driver.findElement(By.id("txtDtSaida")).getAttribute("value")));
				} catch (Exception e) {
					try {
						recopi.setDataSaida(new SimpleDateFormat("dd/MM/yyyy")
								.parse(driver.findElement(By.id("txtDtSaida")).getText()));
					} catch (Exception e2) {
						mensagem("Recopi " + recopi.getControle() + " não possui data saida.");
					}
				}
				// RETORNO e FECHADO				
				try {
					recopi.setDataEntrada(new SimpleDateFormat("dd/MM/yyyy")
							.parse(driver.findElement(By.id("txtDtEntrada")).getAttribute("value")));
				} catch (Exception e) {
					try {
						recopi.setDataEntrada(new SimpleDateFormat("dd/MM/yyyy")
								.parse(driver.findElement(By.id("txtDtEntrada")).getText()));
					} catch (Exception e2) {
						try {
							recopi.setDataEntrada(new SimpleDateFormat("dd/MM/yyyy")
									.parse(driver.findElement(By.xpath("//*[@id=\"ConteudoPrincipal\"]/div/div[22]/div[2]")).getText()));
						} catch (Exception e3) {
							mensagem("Recopi " + recopi.getControle() + " não possui data entrada.");
						}
					}
				}

				recopi.setValor(Double.parseDouble(driver.findElement(By.id("txtValor")).getAttribute("value")
						.replace(".", "").replace(",", ".")));
			}

			// grava os tipos de papeis de cada operação
			linhaPapel = 1;
			while (linhaPapel > 0) {
				// registro atual (a linha 1 refere-se ao cabeçalho da tabela)
				linhaPapel++;

				try {
					mensagem("Encontrado NCM " + driver
							.findElement(
									By.xpath("//*[@id=\"grvOperacoesTipoPapel\"]/tbody/tr[" + linhaPapel + "]/td[1]"))
							.getText() + " no Recopi " + recopi.getControle() + ".");
					RecopiItem recopiItem = new RecopiItem();
					recopiItem.setTipo("REMESSA");
					recopiItem.setControle(recopi.getControle());
					recopiItem.setNotaFiscal(recopi.getNotaFiscal());
					recopiItem.setNcm(driver
							.findElement(
									By.xpath("//*[@id=\"grvOperacoesTipoPapel\"]/tbody/tr[" + linhaPapel + "]/td[1]"))
							.getText());
					recopiItem.setTipoPapel(driver
							.findElement(
									By.xpath("//*[@id=\"grvOperacoesTipoPapel\"]/tbody/tr[" + linhaPapel + "]/td[2]"))
							.getText());
					recopiItem.setQuantidade(Double.parseDouble(driver
							.findElement(
									By.xpath("//*[@id=\"grvOperacoesTipoPapel\"]/tbody/tr[" + linhaPapel + "]/td[3]"))
							.getText().replace(".", "").replace(",", ".")));
					if (!recopi.getSituacao().equals("ABERTO") && !recopi.getSituacao().equals("CANCELADO")
							&& !recopi.getSituacao().equals("RETORNO") && !recopi.getSituacao().equals("FECHADO") && !recopi.getSituacao().equals("OP. NÃO IDENTIFICADA")) {
						recopiItem.setQuantidadeDevolvida(Double.parseDouble(driver
								.findElement(By
										.xpath("//*[@id=\"grvOperacoesTipoPapel\"]/tbody/tr[" + linhaPapel + "]/td[4]"))
								.getText().replace(".", "").replace(",", ".")));
						recopiItem.setQuantidadeAceita(Double.parseDouble(driver
								.findElement(By
										.xpath("//*[@id=\"grvOperacoesTipoPapel\"]/tbody/tr[" + linhaPapel + "]/td[5]"))
								.getText().replace(".", "").replace(",", ".")));
					}
					itens.add(recopiItem);
				} catch (Exception e) {
					linhaPapel = 0;
				}
			}

			// abre a tela de consulta de retorno
			mensagem("Verificando retornos do Recopi " + recopi.getControle() + ".");
			try {
//				executor.executeScript("arguments[0].click();", driver.findElement(By.id("btnRetornoIndustArmaz")));
				driver.findElement(By.id("btnRetornoIndustArmaz")).click();
				linhaPapel = 1;
				linhaNota = 1;
			} catch (Exception e) {
				try {
					driver.findElement(By.id("btnRetornoIndArm")).click();
					linhaPapel = 1;
					linhaNota = 1;
				} catch (Exception e2) {
					mensagem("Não existe retorno para o Recopi " + recopi.getControle() + ".");
					linhaPapel = 0;
					linhaNota = 0;
				}				
			}

			while (linhaPapel > 0) {
				// registro atual (a linha 1 refere-se ao cabeçalho da tabela)
				linhaPapel++;

				try {
					for (RecopiItem recopiItem : itens) {
						if (recopiItem.getControle().equals(recopi.getControle()) && recopiItem.getNcm().equals(
								driver.findElement(By.xpath("//*[@id=\"grvPapel\"]/tbody/tr[" + linhaPapel + "]/td[1]"))
										.getText())) {
							recopiItem.setQuantidadeRecebida(Double.parseDouble(driver
									.findElement(By.xpath("//*[@id=\"grvPapel\"]/tbody/tr[" + linhaPapel + "]/td[4]"))
									.getText().replace(".", "").replace(",", ".")));
							recopiItem.setPerda(Double.parseDouble(driver
									.findElement(By.xpath("//*[@id=\"grvPapel\"]/tbody/tr[" + linhaPapel + "]/td[5]"))
									.getText().replace(".", "").replace(",", ".")));
							recopiItem.setSinistro(Double.parseDouble(driver
									.findElement(By.xpath("//*[@id=\"grvPapel\"]/tbody/tr[" + linhaPapel + "]/td[6]"))
									.getText().replace(".", "").replace(",", ".")));
							recopiItem.setQuantidadeAReceber(Double.parseDouble(driver
									.findElement(By.xpath("//*[@id=\"grvPapel\"]/tbody/tr[" + linhaPapel + "]/td[7]"))
									.getText().replace(".", "").replace(",", ".")));
						}
					}
				} catch (Exception e) {
					linhaPapel = 0;
				}
			}
			
			// adicina os itens na lista final
			for(RecopiItem recopiItem: itens) {
				operacoesItens.add(recopiItem);
			}
			itens.clear();

			// abre as notas de retorno
			while (linhaNota > 0) {
				// registro atual (a linha 1 refere-se ao cabeçalho da tabela)
				linhaNota++;

				try {
					jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
					mensagem("Encontrada nota de retorno " + driver
							.findElement(By.xpath("//*[@id=\"grvRecRetFracionado\"]/tbody/tr[" + linhaNota + "]/td[1]"))
							.getText() + " no Recopi " + recopi.getControle() + ".");
					Recopi retorno = new Recopi();
					retorno.setTipo("RETORNO");
					retorno.setControle(driver.findElement(By.id("lblNumeroregistroControle")).getText());
					retorno.setNotaFiscal(driver
							.findElement(By.xpath("//*[@id=\"grvRecRetFracionado\"]/tbody/tr[" + linhaNota + "]/td[1]"))
							.getText());
					retorno.setSerie(driver
							.findElement(By.xpath("//*[@id=\"grvRecRetFracionado\"]/tbody/tr[" + linhaNota + "]/td[2]"))
							.getText());
					retorno.setValor(Double.parseDouble(driver
							.findElement(By.xpath("//*[@id=\"grvRecRetFracionado\"]/tbody/tr[" + linhaNota + "]/td[4]"))
							.getText().replace(".", "").replace(",", ".")));
					retorno.setSituacao(driver
							.findElement(By.xpath("//*[@id=\"grvRecRetFracionado\"]/tbody/tr[" + linhaNota + "]/td[5]"))
							.getText());
					driver.findElement(By.xpath("//*[@id=\"grvRecRetFracionado\"]/tbody/tr[" + linhaNota + "]/td[6]"))
							.click();

					retorno.setTipoOperacao(driver.findElement(By.id("txtTipoOperacao")).getAttribute("value"));
					retorno.setCfop(
							driver.findElement(By.xpath("//*[@id=\"ConteudoPrincipal\"]/div/div[6]/div[1]")).getText());
					retorno.setDescricao(
							driver.findElement(By.xpath("//*[@id=\"ConteudoPrincipal\"]/div/div[6]/div[2]")).getText());
					retorno.setCnpjOrigem(
							driver.findElement(By.name("ctl00$ConteudoPagina$ctlDadosEstabelecimentoOrigem$txtCNPJ"))
									.getAttribute("value"));
					retorno.setRazaoOrigem(driver
							.findElement(By.name("ctl00$ConteudoPagina$ctlDadosEstabelecimentoOrigem$txtRazaoSocial"))
							.getAttribute("value"));
					retorno.setCnpjDestino(
							driver.findElement(By.name("ctl00$ConteudoPagina$ctlDadosEstabelecimentoDestino$txtCNPJ"))
									.getAttribute("value"));
					retorno.setRazaoDestino(driver
							.findElement(By.name("ctl00$ConteudoPagina$ctlDadosEstabelecimentoDestino$txtRazaoSocial"))
							.getAttribute("value"));

					try {
						retorno.setDataEmissao(new SimpleDateFormat("dd/MM/yyyy")
								.parse(driver.findElement(By.id("txtDtEmissao")).getAttribute("value")));
					} catch (Exception e) {
						try {
							retorno.setDataEmissao(new SimpleDateFormat("dd/MM/yyyy")
									.parse(driver.findElement(By.id("txtDtEmissao")).getText()));
						} catch (Exception e2) {
							mensagem("Nota de retorno " + retorno.getNotaFiscal() + " do Recopi "
									+ retorno.getControle() + " não possui data emissão.");
						}
					}
					try {
						retorno.setDataSaida(new SimpleDateFormat("dd/MM/yyyy")
								.parse(driver.findElement(By.id("txtDtSaida")).getAttribute("value")));
					} catch (Exception e) {
						try {
							retorno.setDataSaida(new SimpleDateFormat("dd/MM/yyyy")
									.parse(driver.findElement(By.id("txtDtSaida")).getText()));
						} catch (Exception e2) {
							mensagem("Nota de retorno " + retorno.getNotaFiscal() + " do Recopi "
									+ retorno.getControle() + " não possui data saída.");
						}
					}
					try {
						retorno.setDataEntrada(new SimpleDateFormat("dd/MM/yyyy")
								.parse(driver.findElement(By.id("txtDtEntrada")).getAttribute("value")));
					} catch (Exception e) {
						try {
							retorno.setDataEntrada(new SimpleDateFormat("dd/MM/yyyy")
									.parse(driver.findElement(By.id("txtDtEntrada")).getText()));
						} catch (Exception e2) {
							mensagem("Nota de retorno " + retorno.getNotaFiscal() + " do Recopi "
									+ retorno.getControle() + " não possui data entrada.");
						}
					}

					operacoes.add(retorno);

					// grava os tipos de papeis de cada operação de retorno
					linhaPapel = 1;
					while (linhaPapel > 0) {
						// registro atual (a linha 1 refere-se ao cabeçalho da tabela)
						linhaPapel++;

						try {
							mensagem("Encontrado NCM "
									+ driver.findElement(
											By.xpath("//*[@id=\"grvOperPapel\"]/tbody/tr[" + linhaPapel + "]/td[1]"))
											.getText()
									+ " na nota de retorno " + retorno.getNotaFiscal() + " no Recopi "
									+ recopi.getControle() + ".");
							RecopiItem recopiItem = new RecopiItem();
							recopiItem.setTipo("RETORNO");
							recopiItem.setControle(retorno.getControle());
							recopiItem.setNotaFiscal(retorno.getNotaFiscal());
							recopiItem.setNcm(driver
									.findElement(
											By.xpath("//*[@id=\"grvOperPapel\"]/tbody/tr[" + linhaPapel + "]/td[1]"))
									.getText());
							recopiItem.setTipoPapel(driver
									.findElement(
											By.xpath("//*[@id=\"grvOperPapel\"]/tbody/tr[" + linhaPapel + "]/td[2]"))
									.getText());
							recopiItem.setQuantidade(Double.parseDouble(driver
									.findElement(
											By.xpath("//*[@id=\"grvOperPapel\"]/tbody/tr[" + linhaPapel + "]/td[3]"))
									.getText().replace(".", "").replace(",", ".")));
							recopiItem.setQuantidadeDevolvida(Double.parseDouble(driver
									.findElement(
											By.xpath("//*[@id=\"grvOperPapel\"]/tbody/tr[" + linhaPapel + "]/td[4]"))
									.getText().replace(".", "").replace(",", ".")));
							recopiItem.setQuantidadeRecebida(Double.parseDouble(driver
									.findElement(
											By.xpath("//*[@id=\"grvOperPapel\"]/tbody/tr[" + linhaPapel + "]/td[7]"))
									.getText().replace(".", "").replace(",", ".")));
							recopiItem.setPerda(Double.parseDouble(driver
									.findElement(
											By.xpath("//*[@id=\"grvOperPapel\"]/tbody/tr[" + linhaPapel + "]/td[8]"))
									.getText().replace(".", "").replace(",", ".")));
							recopiItem.setSinistro(Double.parseDouble(driver
									.findElement(
											By.xpath("//*[@id=\"grvOperPapel\"]/tbody/tr[" + linhaPapel + "]/td[9]"))
									.getText().replace(".", "").replace(",", ".")));
							operacoesItens.add(recopiItem);
						} catch (Exception e) {
							linhaPapel = 0;
						}
					}
					// voltar para a lista de notas de retorno
					jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
					driver.findElement(By.id("btnVoltarParaOperacao")).click();
				} catch (Exception e) {
					linhaNota = 0;
				}
			}
		}

	}

	private void salvarArquivos(File dir) {
		mensagem("Preparando arquivo de download.");

		// salva operações
		try {
			xwb = new HSSFWorkbook();
			HSSFSheet sheet = xwb.createSheet("Operações");
			CellStyle cellStyle = xwb.createCellStyle();
			CreationHelper createHelper = xwb.getCreationHelper();
			Field[] fields = Recopi.class.getDeclaredFields();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy"));

			int rownum = 0;
			int cellnum = 0;

			// cabeçalho do arquivo
			Row row = sheet.createRow(rownum++);
			Cell cell;
			for (Field field : fields) {
				if (field.getName().equals("serialVersionUID"))
					continue;
				cell = row.createCell(cellnum++);
				cell.setCellValue(field.getName());
			}

			// dados do arquivo
			Collections.sort(operacoes, Comparator.comparing(Recopi::getOrdem));
			for (Recopi recopi : operacoes) {
				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getControle());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getTipo());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getCnpjOrigem());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getRazaoOrigem());

//				cell = row.createCell(cellnum++);
//				cell.setCellValue(recopi.getUfOrigem());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getCnpjDestino());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getRazaoDestino());

//				cell = row.createCell(cellnum++);
//				cell.setCellValue(recopi.getUfDestino());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getTipoOperacao());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getFormaRegistro());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getSituacao());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getDias());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getCfop());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getDescricao());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getNotaFiscal());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getSerie());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getDataEmissao());
				cell.setCellStyle(cellStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getDataSaida());
				cell.setCellStyle(cellStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getDataEntrada());
				cell.setCellStyle(cellStyle);

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopi.getValor());
			}

			// tamanho das colunas
			cellnum = 0;
			for (Field field : fields) {
				if (field.getName().equals("serialVersionUID"))
					continue;
				sheet.autoSizeColumn(cellnum++);
			}

			sheet = xwb.createSheet("Itens");
			fields = RecopiItem.class.getDeclaredFields();
			rownum = 0;
			cellnum = 0;

			// cabeçalho do arquivo
			row = sheet.createRow(rownum++);
			for (Field field : fields) {
				if (field.getName().equals("serialVersionUID"))
					continue;
				cell = row.createCell(cellnum++);
				cell.setCellValue(field.getName());
			}

			// dados do arquivo
			Collections.sort(operacoesItens, Comparator.comparing(RecopiItem::getOrdem));
			for (RecopiItem recopiItem : operacoesItens) {
				row = sheet.createRow(rownum++);
				cellnum = 0;

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getControle());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getTipo());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getNcm());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getTipoPapel());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getNotaFiscal());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getQuantidade());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getQuantidadeDevolvida());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getQuantidadeAceita());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getQuantidadeAReceber());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getQuantidadeRecebida());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getPerda());

				cell = row.createCell(cellnum++);
				cell.setCellValue(recopiItem.getSinistro());
			}

			// tamanho das colunas
			cellnum = 0;
			for (Field field : fields) {
				if (field.getName().equals("serialVersionUID"))
					continue;
				sheet.autoSizeColumn(cellnum++);
			}

			FileOutputStream fos = new FileOutputStream(dir + File.separator + arquivoXLS);
			xwb.write(fos);
			fos.close();
			mensagem("Arquivo: " + dir + File.separator + arquivoXLS);
		} catch (FileNotFoundException e) {
			mensagem("Arquivo excel não encontrado!");
		} catch (IOException e) {
			mensagem("Erro na edição do arquivo!");
		}

		// salva arquivo de log
		try {
			PrintWriter pw = new PrintWriter(dir + File.separator + arquivoLOG);
			for (String mensagem : mensagens) {
				pw.println(mensagem);
			}
			pw.close();
		} catch (IOException e) {
			mensagem("Erro na edição do arquivo de LOG!");
		}
	}

	public void finalizar() {
		driver.close();
		driver.quit();
		mensagem("Finalizando coleta de dados do Recopi.");
		if (gravarArquivo)
			salvarArquivos(new File(diretorio));
	}

	public void mensagem(String string) {
		string = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + " - " + string;
//		System.out.println(string);
		mensagens.add(string);
		Aplicacao.setMensagem(string);
	}

	public void executar() {
		try {
			iniciar();
			if (driverEncontrado) {
				driver.get("https://www10.fazenda.sp.gov.br/RecopiNacional/");
				driver.manage().window().maximize();
				informarTelaLogin(consulta);
				fecharTelaAviso();
				abrirTelaConsulta();
				informarTelaConsulta(consulta);
				verificarRetornoTelaConsulta();
				gravarRecopi();
				gravarOperacoes();
				finalizar();
			}
		} catch (Exception e) {
			mensagem("Encontrado algum problema.");
			mensagem("Verifique se o navegador foi encerrado manualmente.");
			mensagem("Verifique se o driver (chromedriver.exe) está desatualizado.");
			mensagem("https://sites.google.com/a/chromium.org/chromedriver/downloads");
		}
	}
}
