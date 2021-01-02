using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Text;
using System.Resources;
using System.Runtime.InteropServices;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using XenoLogin.Properties;

namespace XenoLogin
{
	public class Form1 : Form
	{
		private char[] key = new char[99]
		{
			'µ',
			'¬',
			'\u007f',
			'\u008c',
			'(',
			'\0',
			'\u0083',
			'6',
			'\u008f',
			'U',
			'-',
			'`',
			'\r',
			'|',
			'N',
			'\u008d',
			'ß',
			'î',
			'A',
			'\u0083',
			'Ü',
			'N',
			'\u00b8',
			'i',
			'º',
			'F',
			'+',
			'n',
			'p',
			'a',
			'À',
			'\u0011',
			'p',
			'*',
			'\u008e',
			'Á',
			'ý',
			'½',
			'Z',
			'D',
			'Z',
			'n',
			'q',
			'\u0087',
			' ',
			'®',
			'®',
			'Í',
			'a',
			'.',
			'x',
			'­',
			'\u0095',
			'l',
			'\u009a',
			'á',
			'b',
			'\u008d',
			'\u001e',
			'{',
			'\u0091',
			':',
			'ú',
			'É',
			'\v',
			'\u007f',
			'Å',
			'\a',
			'`',
			'Ú',
			'l',
			'\u001b',
			'b',
			'í',
			'ù',
			'w',
			'\u0082',
			'\u00a0',
			'\u000e',
			'æ',
			'²',
			'$',
			'Ö',
			'Æ',
			'Ë',
			'I',
			'\u008e',
			'\u008b',
			'ú',
			'\u00af',
			'\u0098',
			'+',
			'Á',
			'Ê',
			'æ',
			'+',
			'ù',
			's',
			'\u001f'
		};

		private IContainer components;

		private PrivateFontCollection pfc = (PrivateFontCollection)(object)new PrivateFontCollection();

		private Button button1;

		private Button button2;

		private Button button3;

		private Button button4;

		private Button button5;

		private Button button6;

		private Button button7;

		private Button button8;

		private Button button9;

		private Button button10;

		private Button button11;

		private Button button12;

		private Button button13;

		private Button button14;

		private Button button15;

		private Button button16;

		private Label label1;

		private PictureBox pictureBox1;

		private TextBox textBox1;

		public Form1()
			: this()
		{
			//IL_0019: Unknown result type (might be due to invalid IL or missing references)
			//IL_0023: Expected O, but got Unknown
			InitializeComponent();
		}

		public static char[] RC4(char[] input, char[] key)
		{
			List<char> list = new List<char>();
			int num = 0;
			int[] array = new int[256];
			for (int i = 0; i < 256; i++)
			{
				array[i] = i;
			}
			for (int j = 0; j < 256; j++)
			{
				num = (key[j % key.Length] + array[j] + num) % 256;
				int num2 = array[j];
				array[j] = array[num];
				array[num] = num2;
			}
			for (int k = 0; k < input.Length; k++)
			{
				int num3 = k % 256;
				num = (array[num3] + num) % 256;
				int num2 = array[num3];
				array[num3] = array[num];
				array[num] = num2;
				list.Add((char)(input[k] ^ array[(array[num3] + array[num]) % 256]));
			}
			return list.ToArray();
		}

		private static string GetMd5Hash(MD5 md5Hash, string input)
		{
			byte[] array = ((HashAlgorithm)md5Hash).ComputeHash(Encoding.UTF8.GetBytes(input));
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < array.Length; i++)
			{
				stringBuilder.Append(array[i].ToString("x2"));
			}
			return stringBuilder.ToString();
		}

		private void EnteredSymbol(string symbol_line)
		{
			//IL_00d8: Unknown result type (might be due to invalid IL or missing references)
			//IL_00f5: Unknown result type (might be due to invalid IL or missing references)
			string[] array = new string[8]
			{
				"06fa567b72d78b7e3ea746973fbbd1d5",
				"142ba1ee3860caecc3f86d7a03b5b175",
				"54229abfcfa5649e7003b83dd4755294",
				"8d2b901035fbd2df68a3b02940ff5196",
				"727999d580f3708378e3d903ddfa246d",
				"ea8a1a99f6c94c275a58dcd78f418c1f",
				"c51ce410c124a10e0db5e4b97fc2af39",
				"a5bfc9e07964f8dddeb95fc584cd965d"
			};
			int length = symbol_line.Length;
			switch (length)
			{
			case 0:
				return;
			case 16:
				((Control)textBox1).set_Text(Encoding.ASCII.GetString(Encoding.ASCII.GetBytes(RC4(key, Encoding.ASCII.GetChars(Encoding.ASCII.GetBytes(symbol_line))))));
				((Control)textBox1).set_Visible(true);
				return;
			}
			if (length % 2 != 0)
			{
				return;
			}
			string input = symbol_line.Substring(length - 2, 2);
			if (GetMd5Hash(MD5.Create(), input) != array[(length - 2) / 2])
			{
				for (int i = 0; i < 5; i++)
				{
					((Control)label1).set_ForeColor(Color.get_Red());
					((Control)this).Refresh();
					Thread.Sleep(100);
					((Control)label1).set_ForeColor(Color.get_Black());
					((Control)this).Refresh();
					Thread.Sleep(100);
				}
				((Control)label1).set_Text(symbol_line.Substring(0, length - 2));
			}
		}

		[DllImport("gdi32.dll")]
		private static extern IntPtr AddFontMemResourceEx(IntPtr pbFont, uint cbFont, IntPtr pdv, [In] ref uint pcFonts);

		private void Form1_Load(object sender, EventArgs e)
		{
			//IL_005d: Unknown result type (might be due to invalid IL or missing references)
			//IL_0063: Expected O, but got Unknown
			IntPtr intPtr = Marshal.AllocCoTaskMem(Resources.klingon_font.Length);
			Marshal.Copy(Resources.klingon_font, 0, intPtr, Resources.klingon_font.Length);
			uint pcFonts = 0u;
			AddFontMemResourceEx(intPtr, (uint)Resources.klingon_font.Length, IntPtr.Zero, ref pcFonts);
			pfc.AddMemoryFont(intPtr, Resources.klingon_font.Length);
			Font font = (Font)(object)new Font(((FontCollection)pfc).get_Families()[0], 32f, (FontStyle)0);
			((Control)label1).set_Font(font);
			((Control)button1).set_Font(font);
			((Control)button1).set_Text("0");
			((Control)button2).set_Font(font);
			((Control)button2).set_Text("1");
			((Control)button3).set_Font(font);
			((Control)button3).set_Text("2");
			((Control)button4).set_Font(font);
			((Control)button4).set_Text("3");
			((Control)button5).set_Font(font);
			((Control)button5).set_Text("4");
			((Control)button6).set_Font(font);
			((Control)button6).set_Text("5");
			((Control)button7).set_Font(font);
			((Control)button7).set_Text("6");
			((Control)button8).set_Font(font);
			((Control)button8).set_Text("7");
			((Control)button9).set_Font(font);
			((Control)button9).set_Text("8");
			((Control)button10).set_Font(font);
			((Control)button10).set_Text("9");
			((Control)button11).set_Font(font);
			((Control)button11).set_Text("A");
			((Control)button12).set_Font(font);
			((Control)button12).set_Text("B");
			((Control)button13).set_Font(font);
			((Control)button13).set_Text("C");
			((Control)button14).set_Font(font);
			((Control)button14).set_Text("D");
			((Control)button15).set_Font(font);
			((Control)button15).set_Text("E");
			((Control)button16).set_Font(font);
			((Control)button16).set_Text("F");
		}

		private void button1_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button1).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button2_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button2).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button3_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button3).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button4_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button4).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button5_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button5).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button6_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button6).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button7_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button7).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button8_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button8).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button9_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button9).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button10_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button10).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button11_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button11).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button12_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button12).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button13_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button13).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button14_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button14).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button15_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button15).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		private void button16_Click(object sender, EventArgs e)
		{
			Label obj = label1;
			((Control)obj).set_Text(((Control)obj).get_Text() + ((Control)button16).get_Text());
			EnteredSymbol(((Control)label1).get_Text());
		}

		protected override void Dispose(bool disposing)
		{
			if (disposing && components != null)
			{
				((IDisposable)components).Dispose();
			}
			((Form)this).Dispose(disposing);
		}

		private void InitializeComponent()
		{
			//IL_000a: Unknown result type (might be due to invalid IL or missing references)
			//IL_0010: Expected O, but got Unknown
			//IL_0011: Unknown result type (might be due to invalid IL or missing references)
			//IL_001b: Expected O, but got Unknown
			//IL_001c: Unknown result type (might be due to invalid IL or missing references)
			//IL_0026: Expected O, but got Unknown
			//IL_0027: Unknown result type (might be due to invalid IL or missing references)
			//IL_0031: Expected O, but got Unknown
			//IL_0032: Unknown result type (might be due to invalid IL or missing references)
			//IL_003c: Expected O, but got Unknown
			//IL_003d: Unknown result type (might be due to invalid IL or missing references)
			//IL_0047: Expected O, but got Unknown
			//IL_0048: Unknown result type (might be due to invalid IL or missing references)
			//IL_0052: Expected O, but got Unknown
			//IL_0053: Unknown result type (might be due to invalid IL or missing references)
			//IL_005d: Expected O, but got Unknown
			//IL_005e: Unknown result type (might be due to invalid IL or missing references)
			//IL_0068: Expected O, but got Unknown
			//IL_0069: Unknown result type (might be due to invalid IL or missing references)
			//IL_0073: Expected O, but got Unknown
			//IL_0074: Unknown result type (might be due to invalid IL or missing references)
			//IL_007e: Expected O, but got Unknown
			//IL_007f: Unknown result type (might be due to invalid IL or missing references)
			//IL_0089: Expected O, but got Unknown
			//IL_008a: Unknown result type (might be due to invalid IL or missing references)
			//IL_0094: Expected O, but got Unknown
			//IL_0095: Unknown result type (might be due to invalid IL or missing references)
			//IL_009f: Expected O, but got Unknown
			//IL_00a0: Unknown result type (might be due to invalid IL or missing references)
			//IL_00aa: Expected O, but got Unknown
			//IL_00ab: Unknown result type (might be due to invalid IL or missing references)
			//IL_00b5: Expected O, but got Unknown
			//IL_00b6: Unknown result type (might be due to invalid IL or missing references)
			//IL_00c0: Expected O, but got Unknown
			//IL_00c1: Unknown result type (might be due to invalid IL or missing references)
			//IL_00cb: Expected O, but got Unknown
			//IL_00cc: Unknown result type (might be due to invalid IL or missing references)
			//IL_00d6: Expected O, but got Unknown
			//IL_00d7: Unknown result type (might be due to invalid IL or missing references)
			//IL_00e1: Expected O, but got Unknown
			//IL_00fc: Unknown result type (might be due to invalid IL or missing references)
			//IL_0120: Unknown result type (might be due to invalid IL or missing references)
			//IL_0176: Unknown result type (might be due to invalid IL or missing references)
			//IL_019a: Unknown result type (might be due to invalid IL or missing references)
			//IL_01f0: Unknown result type (might be due to invalid IL or missing references)
			//IL_0214: Unknown result type (might be due to invalid IL or missing references)
			//IL_026a: Unknown result type (might be due to invalid IL or missing references)
			//IL_028e: Unknown result type (might be due to invalid IL or missing references)
			//IL_02e7: Unknown result type (might be due to invalid IL or missing references)
			//IL_030b: Unknown result type (might be due to invalid IL or missing references)
			//IL_0364: Unknown result type (might be due to invalid IL or missing references)
			//IL_0388: Unknown result type (might be due to invalid IL or missing references)
			//IL_03e1: Unknown result type (might be due to invalid IL or missing references)
			//IL_0405: Unknown result type (might be due to invalid IL or missing references)
			//IL_045b: Unknown result type (might be due to invalid IL or missing references)
			//IL_047f: Unknown result type (might be due to invalid IL or missing references)
			//IL_04d8: Unknown result type (might be due to invalid IL or missing references)
			//IL_04fc: Unknown result type (might be due to invalid IL or missing references)
			//IL_0556: Unknown result type (might be due to invalid IL or missing references)
			//IL_057a: Unknown result type (might be due to invalid IL or missing references)
			//IL_05d4: Unknown result type (might be due to invalid IL or missing references)
			//IL_05f8: Unknown result type (might be due to invalid IL or missing references)
			//IL_064f: Unknown result type (might be due to invalid IL or missing references)
			//IL_0673: Unknown result type (might be due to invalid IL or missing references)
			//IL_06cc: Unknown result type (might be due to invalid IL or missing references)
			//IL_06f0: Unknown result type (might be due to invalid IL or missing references)
			//IL_074a: Unknown result type (might be due to invalid IL or missing references)
			//IL_076e: Unknown result type (might be due to invalid IL or missing references)
			//IL_07c8: Unknown result type (might be due to invalid IL or missing references)
			//IL_07ec: Unknown result type (might be due to invalid IL or missing references)
			//IL_0843: Unknown result type (might be due to invalid IL or missing references)
			//IL_0867: Unknown result type (might be due to invalid IL or missing references)
			//IL_08d4: Unknown result type (might be due to invalid IL or missing references)
			//IL_08de: Expected O, but got Unknown
			//IL_08e8: Unknown result type (might be due to invalid IL or missing references)
			//IL_090b: Unknown result type (might be due to invalid IL or missing references)
			//IL_093f: Unknown result type (might be due to invalid IL or missing references)
			//IL_0969: Unknown result type (might be due to invalid IL or missing references)
			//IL_09a3: Unknown result type (might be due to invalid IL or missing references)
			//IL_09ad: Expected O, but got Unknown
			//IL_09b7: Unknown result type (might be due to invalid IL or missing references)
			//IL_09f6: Unknown result type (might be due to invalid IL or missing references)
			//IL_0a24: Unknown result type (might be due to invalid IL or missing references)
			//IL_0a40: Unknown result type (might be due to invalid IL or missing references)
			//IL_0b99: Unknown result type (might be due to invalid IL or missing references)
			//IL_0ba3: Expected O, but got Unknown
			ComponentResourceManager val = (ComponentResourceManager)(object)new ComponentResourceManager(typeof(Form1));
			button1 = (Button)(object)new Button();
			button2 = (Button)(object)new Button();
			button3 = (Button)(object)new Button();
			button4 = (Button)(object)new Button();
			button5 = (Button)(object)new Button();
			button6 = (Button)(object)new Button();
			button7 = (Button)(object)new Button();
			button8 = (Button)(object)new Button();
			button9 = (Button)(object)new Button();
			button10 = (Button)(object)new Button();
			button11 = (Button)(object)new Button();
			button12 = (Button)(object)new Button();
			button13 = (Button)(object)new Button();
			button14 = (Button)(object)new Button();
			button15 = (Button)(object)new Button();
			button16 = (Button)(object)new Button();
			label1 = (Label)(object)new Label();
			pictureBox1 = (PictureBox)(object)new PictureBox();
			textBox1 = (TextBox)(object)new TextBox();
			((ISupportInitialize)pictureBox1).BeginInit();
			((Control)this).SuspendLayout();
			((Control)button1).set_Location(new Point(68, 78));
			((Control)button1).set_Name("button1");
			((Control)button1).set_Size(new Size(50, 45));
			((Control)button1).set_TabIndex(0);
			((Control)button1).set_Text("1");
			((ButtonBase)button1).set_UseVisualStyleBackColor(true);
			((Control)button1).add_Click((EventHandler)button1_Click);
			((Control)button2).set_Location(new Point(133, 78));
			((Control)button2).set_Name("button2");
			((Control)button2).set_Size(new Size(50, 45));
			((Control)button2).set_TabIndex(1);
			((Control)button2).set_Text("button2");
			((ButtonBase)button2).set_UseVisualStyleBackColor(true);
			((Control)button2).add_Click((EventHandler)button2_Click);
			((Control)button3).set_Location(new Point(202, 78));
			((Control)button3).set_Name("button3");
			((Control)button3).set_Size(new Size(50, 45));
			((Control)button3).set_TabIndex(2);
			((Control)button3).set_Text("button3");
			((ButtonBase)button3).set_UseVisualStyleBackColor(true);
			((Control)button3).add_Click((EventHandler)button3_Click);
			((Control)button4).set_Location(new Point(268, 78));
			((Control)button4).set_Name("button4");
			((Control)button4).set_Size(new Size(50, 45));
			((Control)button4).set_TabIndex(3);
			((Control)button4).set_Text("button4");
			((ButtonBase)button4).set_UseVisualStyleBackColor(true);
			((Control)button4).add_Click((EventHandler)button4_Click);
			((Control)button5).set_Location(new Point(268, 139));
			((Control)button5).set_Name("button5");
			((Control)button5).set_Size(new Size(50, 45));
			((Control)button5).set_TabIndex(7);
			((Control)button5).set_Text("button5");
			((ButtonBase)button5).set_UseVisualStyleBackColor(true);
			((Control)button5).add_Click((EventHandler)button5_Click);
			((Control)button6).set_Location(new Point(202, 139));
			((Control)button6).set_Name("button6");
			((Control)button6).set_Size(new Size(50, 45));
			((Control)button6).set_TabIndex(6);
			((Control)button6).set_Text("button6");
			((ButtonBase)button6).set_UseVisualStyleBackColor(true);
			((Control)button6).add_Click((EventHandler)button6_Click);
			((Control)button7).set_Location(new Point(133, 139));
			((Control)button7).set_Name("button7");
			((Control)button7).set_Size(new Size(50, 45));
			((Control)button7).set_TabIndex(5);
			((Control)button7).set_Text("button7");
			((ButtonBase)button7).set_UseVisualStyleBackColor(true);
			((Control)button7).add_Click((EventHandler)button7_Click);
			((Control)button8).set_Location(new Point(68, 139));
			((Control)button8).set_Name("button8");
			((Control)button8).set_Size(new Size(50, 45));
			((Control)button8).set_TabIndex(4);
			((Control)button8).set_Text("button8");
			((ButtonBase)button8).set_UseVisualStyleBackColor(true);
			((Control)button8).add_Click((EventHandler)button8_Click);
			((Control)button9).set_Location(new Point(268, 200));
			((Control)button9).set_Name("button9");
			((Control)button9).set_Size(new Size(50, 45));
			((Control)button9).set_TabIndex(11);
			((Control)button9).set_Text("button9");
			((ButtonBase)button9).set_UseVisualStyleBackColor(true);
			((Control)button9).add_Click((EventHandler)button9_Click);
			((Control)button10).set_Location(new Point(202, 200));
			((Control)button10).set_Name("button10");
			((Control)button10).set_Size(new Size(50, 45));
			((Control)button10).set_TabIndex(10);
			((Control)button10).set_Text("button10");
			((ButtonBase)button10).set_UseVisualStyleBackColor(true);
			((Control)button10).add_Click((EventHandler)button10_Click);
			((Control)button11).set_Location(new Point(133, 200));
			((Control)button11).set_Name("button11");
			((Control)button11).set_Size(new Size(50, 45));
			((Control)button11).set_TabIndex(9);
			((Control)button11).set_Text("button11");
			((ButtonBase)button11).set_UseVisualStyleBackColor(true);
			((Control)button11).add_Click((EventHandler)button11_Click);
			((Control)button12).set_Location(new Point(68, 200));
			((Control)button12).set_Name("button12");
			((Control)button12).set_Size(new Size(50, 45));
			((Control)button12).set_TabIndex(8);
			((Control)button12).set_Text("button12");
			((ButtonBase)button12).set_UseVisualStyleBackColor(true);
			((Control)button12).add_Click((EventHandler)button12_Click);
			((Control)button13).set_Location(new Point(268, 260));
			((Control)button13).set_Name("button13");
			((Control)button13).set_Size(new Size(50, 45));
			((Control)button13).set_TabIndex(15);
			((Control)button13).set_Text("button13");
			((ButtonBase)button13).set_UseVisualStyleBackColor(true);
			((Control)button13).add_Click((EventHandler)button13_Click);
			((Control)button14).set_Location(new Point(202, 260));
			((Control)button14).set_Name("button14");
			((Control)button14).set_Size(new Size(50, 45));
			((Control)button14).set_TabIndex(14);
			((Control)button14).set_Text("button14");
			((ButtonBase)button14).set_UseVisualStyleBackColor(true);
			((Control)button14).add_Click((EventHandler)button14_Click);
			((Control)button15).set_Location(new Point(133, 260));
			((Control)button15).set_Name("button15");
			((Control)button15).set_Size(new Size(50, 45));
			((Control)button15).set_TabIndex(13);
			((Control)button15).set_Text("button15");
			((ButtonBase)button15).set_UseVisualStyleBackColor(true);
			((Control)button15).add_Click((EventHandler)button15_Click);
			((Control)button16).set_Location(new Point(68, 260));
			((Control)button16).set_Name("button16");
			((Control)button16).set_Size(new Size(50, 45));
			((Control)button16).set_TabIndex(12);
			((Control)button16).set_Text("button16");
			((ButtonBase)button16).set_UseVisualStyleBackColor(true);
			((Control)button16).add_Click((EventHandler)button16_Click);
			((Control)label1).set_AutoSize(true);
			((Control)label1).set_Font((Font)(object)new Font("Microsoft Sans Serif", 20.25f, (FontStyle)0, (GraphicsUnit)3, (byte)204));
			((Control)label1).set_Location(new Point(31, 16));
			((Control)label1).set_Name("label1");
			((Control)label1).set_Size(new Size(0, 31));
			((Control)label1).set_TabIndex(17);
			pictureBox1.set_Image((Image)(object)Resources.Klingon_Empire_logo);
			((Control)pictureBox1).set_Location(new Point(332, 87));
			((Control)pictureBox1).set_Name("pictureBox1");
			((Control)pictureBox1).set_Size(new Size(161, 206));
			pictureBox1.set_TabIndex(18);
			pictureBox1.set_TabStop(false);
			((Control)textBox1).set_Font((Font)(object)new Font("Microsoft Sans Serif", 12f, (FontStyle)0, (GraphicsUnit)3, (byte)204));
			((Control)textBox1).set_Location(new Point(26, 12));
			((TextBoxBase)textBox1).set_Multiline(true);
			((Control)textBox1).set_Name("textBox1");
			((TextBoxBase)textBox1).set_ReadOnly(true);
			((Control)textBox1).set_Size(new Size(456, 60));
			((Control)textBox1).set_TabIndex(19);
			((Control)textBox1).set_Visible(false);
			((ContainerControl)this).set_AutoScaleDimensions(new SizeF(6f, 13f));
			((ContainerControl)this).set_AutoScaleMode((AutoScaleMode)1);
			((Form)this).set_ClientSize(new Size(505, 357));
			((Control)this).get_Controls().Add((Control)(object)textBox1);
			((Control)this).get_Controls().Add((Control)(object)pictureBox1);
			((Control)this).get_Controls().Add((Control)(object)label1);
			((Control)this).get_Controls().Add((Control)(object)button13);
			((Control)this).get_Controls().Add((Control)(object)button14);
			((Control)this).get_Controls().Add((Control)(object)button15);
			((Control)this).get_Controls().Add((Control)(object)button16);
			((Control)this).get_Controls().Add((Control)(object)button9);
			((Control)this).get_Controls().Add((Control)(object)button10);
			((Control)this).get_Controls().Add((Control)(object)button11);
			((Control)this).get_Controls().Add((Control)(object)button12);
			((Control)this).get_Controls().Add((Control)(object)button5);
			((Control)this).get_Controls().Add((Control)(object)button6);
			((Control)this).get_Controls().Add((Control)(object)button7);
			((Control)this).get_Controls().Add((Control)(object)button8);
			((Control)this).get_Controls().Add((Control)(object)button4);
			((Control)this).get_Controls().Add((Control)(object)button3);
			((Control)this).get_Controls().Add((Control)(object)button2);
			((Control)this).get_Controls().Add((Control)(object)button1);
			((Form)this).set_Icon((Icon)(object)(Icon)((ResourceManager)(object)val).GetObject("$this.Icon"));
			((Control)this).set_Name("Form1");
			((Control)this).set_Text("Klingon login panel");
			((Form)this).add_Load((EventHandler)Form1_Load);
			((ISupportInitialize)pictureBox1).EndInit();
			((Control)this).ResumeLayout(false);
			((Control)this).PerformLayout();
		}
	}
}
