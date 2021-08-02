package program;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entitie.RecopiConsulta;
import test.RecopiTest;
import util.BoundsPopupMenuListener;
import util.DateLabelFormatter;
import javax.swing.JCheckBox;


public class Aplicacao {

	private JFrame frmSelenium;
	private JPanel panel;
	private JLabel lblUsuario;
	private JTextField txtUsuario;
	private JLabel lblSenha;
	private JPasswordField pwdSenha;
	private JLabel lblRecopi;
	private JTextField txtRecopi;
	private JLabel lblMesano;
	private JFormattedTextField frmtdtxtfldMesano;
	private JLabel lblDtInicial;
	private UtilDateModel dateModelInicial;
	private JDatePanelImpl datePanelInicial;
	private JDatePickerImpl datePickerInicial;
	private JLabel lblDtFinal;
	private UtilDateModel dateModelFinal;
	private JDatePanelImpl datePanelFinal;
	private JDatePickerImpl datePickerFinal;
	private JLabel lblSituacao;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbBoxSituacao;
	private String[] situacoes;
	private JLabel lblTipo;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbBoxTipo;
	private String[] tipos;
	private JCheckBox chckbxBrowser;
	private JSeparator separatorLogin;
	private JSeparator separator;
	private JButton btnExecutar;
	private static JScrollPane scrollPane;
	private static JEditorPane dtrpnMensagem;
	private Date mesano;
	private Date dataInicial;
	private Date dataFinal;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@SuppressWarnings("unused")
			@Override
			public void run() {
				Aplicacao window = new Aplicacao();
			}
		});
	}

	public static void setMensagem(String string) {
		try {
			Document doc = dtrpnMensagem.getDocument();
			doc.insertString(0, string + "\n", null);
			dtrpnMensagem.update(dtrpnMensagem.getGraphics());
			dtrpnMensagem.select(0, 0);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public Aplicacao() {
		UIManager.put("Button.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("ToggleButton.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("RadioButton.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("CheckBox.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("ColorChooser.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("ComboBox.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("Label.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("List.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("MenuBar.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("MenuItem.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("RadioButtonMenuItem.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("CheckBoxMenuItem.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("Menu.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("PopupMenu.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("OptionPane.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("Panel.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("ProgressBar.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("ScrollPane.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("Viewport.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("TabbedPane.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("Table.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("TableHeader.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("TextField.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("PasswordField.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("TextArea.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("TextPane.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("EditorPane.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("TitledBorder.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("ToolBar.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("ToolTip.font", new Font("Tahoma", Font.PLAIN, 11));
		UIManager.put("Tree.font", new Font("Tahoma", Font.PLAIN, 11));
		initialize();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frmSelenium = new JFrame();
		frmSelenium.setResizable(false);
		frmSelenium.setTitle("RECOPI v2.05");
		frmSelenium.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSelenium.setSize(600, 400);
		frmSelenium.setVisible(true);
		frmSelenium.setLocationRelativeTo(null);
		frmSelenium.getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		panel.setLayout(null);
		frmSelenium.getContentPane().add(panel);

		lblUsuario = new JLabel("Usuário:");
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setBounds(10, 11, 46, 14);
		panel.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(66, 8, 174, 20);
		txtUsuario.setColumns(10);
		txtUsuario.addKeyListener(new KeyPolicy());
		panel.add(txtUsuario);

		lblSenha = new JLabel("Senha:");
		lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenha.setBounds(250, 11, 46, 14);
		panel.add(lblSenha);

		pwdSenha = new JPasswordField();
		pwdSenha.setBounds(306, 8, 174, 20);
		pwdSenha.addKeyListener(new KeyPolicy());
		panel.add(pwdSenha);

		lblRecopi = new JLabel("Recopi:");
		lblRecopi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRecopi.setBounds(10, 48, 46, 14);
		panel.add(lblRecopi);

		txtRecopi = new JTextField();
		txtRecopi.setBounds(66, 45, 174, 20);
		txtRecopi.addKeyListener(new KeyPolicy());
		panel.add(txtRecopi);

		lblMesano = new JLabel("Mês/Ano:");
		lblMesano.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMesano.setBounds(250, 48, 46, 14);
		panel.add(lblMesano);

		try {
			frmtdtxtfldMesano = new JFormattedTextField(new MaskFormatter("##/####"));
			frmtdtxtfldMesano.setFont(new Font("Tahoma", Font.PLAIN, 11));
			frmtdtxtfldMesano.setFocusLostBehavior(JFormattedTextField.PERSIST);
			frmtdtxtfldMesano.setBounds(306, 45, 89, 20);
			frmtdtxtfldMesano.addKeyListener(new KeyPolicy());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		panel.add(frmtdtxtfldMesano);
		
		lblDtInicial = new JLabel("Dt.Inicial:");
		lblDtInicial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDtInicial.setBounds(10, 76, 46, 14);
		panel.add(lblDtInicial);
		
		Properties p = new Properties();
		p.put("text.today", "Hoje");
		p.put("text.month", "Mês");
		p.put("text.year", "Ano");
		
		dateModelInicial = new UtilDateModel();
		datePanelInicial = new JDatePanelImpl(dateModelInicial, p);
		datePickerInicial = new JDatePickerImpl(datePanelInicial, new DateLabelFormatter());
		datePickerInicial.getJFormattedTextField().setFont(new Font("Tahoma", Font.PLAIN, 11));
		datePickerInicial.setShowYearButtons(true);
		datePickerInicial.setBounds(66, 70, 174, 25);
		panel.add(datePickerInicial);
		
		lblDtFinal = new JLabel("Dt.Final:");
		lblDtFinal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDtFinal.setBounds(250, 76, 46, 14);
		panel.add(lblDtFinal);
		
		dateModelFinal = new UtilDateModel();
		datePanelFinal = new JDatePanelImpl(dateModelFinal, p);
		datePickerFinal = new JDatePickerImpl(datePanelFinal, new DateLabelFormatter());
		datePickerFinal.getJFormattedTextField().setFont(new Font("Tahoma", Font.PLAIN, 11));
		datePickerFinal.setShowYearButtons(true);
		datePickerFinal.setBounds(306, 70, 174, 25);
		panel.add(datePickerFinal);		

		lblSituacao = new JLabel("Situação:");
		lblSituacao.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSituacao.setBounds(10, 103, 46, 14);
		panel.add(lblSituacao);

		situacoes = new String[] { "Selecione", "ABERTO", "ABERTO NF", "CANCELADO", "FECHADO", "SINISTRO", "RETORNO",
				"IMPOSTO DEVIDO", "ACEITO", "OP. NÃO IDENTIFICADA", "DEVOLVIDO", "DEVOLVIDO OK",
				"DEVOLVIDO APOS ACEITE", "DEVOLVIDO APOS ACEITE OK", "FRACIONADO" };
		cmbBoxSituacao = new JComboBox(situacoes);
		cmbBoxSituacao.setBounds(66, 100, 174, 20);
		cmbBoxSituacao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cmbBoxSituacao.setPrototypeDisplayValue(cmbBoxTipo.getSelectedItem());
			}
		});

		lblTipo = new JLabel("Tipo:");
		lblTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipo.setBounds(250, 103, 46, 14);
		lblTipo.addKeyListener(new KeyPolicy());
		panel.add(lblTipo);

		tipos = new String[] { "Selecione", "Remessa", "Remessa Industrialização",
				"Remessa Armazém Geral e Depósito Fechado", "Recebimento", "Recebimento Industrialização",
				"Recebimento Armazém Geral e Depósito Fechado" };
		cmbBoxTipo = new JComboBox(tipos);
		cmbBoxTipo.setBounds(306, 100, 174, 20);
		cmbBoxTipo.addKeyListener(new KeyPolicy());
		cmbBoxTipo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cmbBoxTipo.setPrototypeDisplayValue(cmbBoxTipo.getSelectedItem());
			}
		});

		BoundsPopupMenuListener listenerPopup = new BoundsPopupMenuListener(true, false);
		cmbBoxSituacao.addPopupMenuListener(listenerPopup);
		cmbBoxSituacao.setPrototypeDisplayValue(cmbBoxTipo.getSelectedItem());
		cmbBoxTipo.addPopupMenuListener(listenerPopup);
		cmbBoxTipo.setPrototypeDisplayValue(cmbBoxTipo.getSelectedItem());
		panel.add(cmbBoxSituacao);
		panel.add(cmbBoxTipo);
		
		chckbxBrowser = new JCheckBox("Navegador");
		chckbxBrowser.setBounds(495, 99, 89, 23);
		panel.add(chckbxBrowser);

		btnExecutar = new JButton("Executar");
		btnExecutar.setBounds(495, 72, 89, 23);
		btnExecutar.setMnemonic(KeyEvent.VK_E);
		btnExecutar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dtrpnMensagem.setText("");
				try {
					if (frmtdtxtfldMesano.getText().trim().length() == 7)
						mesano = new SimpleDateFormat("MM/yyyy").parse(frmtdtxtfldMesano.getText());
					dataInicial = (Date) datePickerInicial.getModel().getValue();
					dataFinal = (Date) datePickerFinal.getModel().getValue();
				} catch (ParseException e2) {
					setMensagem("Não foi possível obter os períodos.");
				}
				if (txtUsuario.getText().trim().isEmpty() || pwdSenha.getPassword().length == 0) {
					JOptionPane.showMessageDialog(null, "Favor informar usuário e senha.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (!txtRecopi.getText().trim().isEmpty() && (!frmtdtxtfldMesano.getText().trim().equals("/") || datePickerInicial.getModel().getValue()!=null)) {
					JOptionPane.showMessageDialog(null, "Favor informar Recopi ou Período.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (txtRecopi.getText().trim().isEmpty() && frmtdtxtfldMesano.getText().trim().equals("/") && datePickerInicial.getModel().getValue()==null) {
					JOptionPane.showMessageDialog(null, "Favor informar Recopi ou Período.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (!frmtdtxtfldMesano.getText().trim().equals("/") && datePickerInicial.getModel().getValue()!=null) {
					JOptionPane.showMessageDialog(null, "Favor informar Mês/Ano ou Período.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (frmtdtxtfldMesano.getText().trim().length() > 1
						&& frmtdtxtfldMesano.getText().trim().length() < 7) {
					JOptionPane.showMessageDialog(null, "Favor informar mês e ano válido.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (datePickerInicial.getModel().getValue()!=null && datePickerFinal.getModel().getValue()==null) {
					JOptionPane.showMessageDialog(null, "Favor informar data inicial e final.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (datePickerInicial.getModel().getValue()==null && datePickerFinal.getModel().getValue()!=null) {
					JOptionPane.showMessageDialog(null, "Favor informar data inicial e final.", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					RecopiConsulta consulta = new RecopiConsulta();
					consulta.setUsuario(txtUsuario.getText().trim());
					consulta.setSenha(new String(pwdSenha.getPassword()));
					consulta.setControle(txtRecopi.getText().trim());
					consulta.setMesAno(mesano);
					consulta.setDataInicial(dataInicial);
					consulta.setDataFinal(dataFinal);
					consulta.setBrowser(chckbxBrowser.isSelected());
					if (!cmbBoxSituacao.getSelectedItem().toString().equals("Selecione"))
						consulta.setSituacao(cmbBoxSituacao.getSelectedItem().toString());
					if (!cmbBoxTipo.getSelectedItem().toString().equals("Selecione"))
						consulta.setTipoOperacao(cmbBoxTipo.getSelectedItem().toString());
					RecopiTest test = new RecopiTest(consulta);
					test.executar();
				}
			}
		});
		panel.add(btnExecutar);
		
		separatorLogin = new JSeparator();
		separatorLogin.setBounds(10, 36, 574, 2);
		panel.add(separatorLogin);

		separator = new JSeparator();
		separator.setBounds(10, 128, 574, 2);
		panel.add(separator);

		dtrpnMensagem = new JEditorPane();
		dtrpnMensagem.setEditable(false);
		dtrpnMensagem.addHyperlinkListener(new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(e.getURL().toURI());
					} catch (IOException | URISyntaxException e1) {
						JOptionPane.showMessageDialog(null, "Navegador não localizado.", "Erro",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		scrollPane = new JScrollPane(dtrpnMensagem);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 141, 574, 219);
		panel.add(scrollPane);		

		frmSelenium.setFocusTraversalPolicy(new FocusPolicy());
	}

	class FocusPolicy extends FocusTraversalPolicy {

		private final java.util.List<Component> componentes;
		private int focado = 0;

		public FocusPolicy() {
			this.componentes = new LinkedList<>();
			this.componentes.add(txtUsuario);
			this.componentes.add(pwdSenha);
			this.componentes.add(txtRecopi);
			this.componentes.add(frmtdtxtfldMesano);
			this.componentes.add(cmbBoxSituacao);
			this.componentes.add(cmbBoxTipo);
			this.componentes.add(chckbxBrowser);
			this.componentes.add(btnExecutar);
		}

		@Override
		public Component getComponentAfter(Container aContainer, Component aComponent) {
			this.focado = (this.focado + 1) % this.componentes.size();
			return this.componentes.get(focado);
		}

		@Override
		public Component getComponentBefore(Container aContainer, Component aComponent) {
			this.focado = (this.componentes.size() + this.focado - 1) % this.componentes.size();
			return this.componentes.get(focado);
		}

		@Override
		public Component getDefaultComponent(Container aContainer) {
			return this.componentes.get(0);
		}

		@Override
		public Component getFirstComponent(Container aContainer) {
			return this.componentes.get(0);
		}

		@Override
		public Component getLastComponent(Container aContainer) {
			return this.componentes.get(this.componentes.size() - 1);
		}
	}

	class KeyPolicy extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER)				
				btnExecutar.doClick();
		}
	}
}
