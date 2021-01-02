using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Text;
using System.Runtime.InteropServices;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using XenoLogin.XenoLogin.Properties;

namespace XenoLogin.XenoLogin
{
    public class Form1 : Form
    {
        private readonly char[] buttonKey =
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

        private readonly IContainer components;

        private readonly PrivateFontCollection buttonPfc = new PrivateFontCollection();

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

        private Label buttonLabel1;

        private PictureBox buttonPictureBox1;

        private TextBox buttonTextBox1;

        public Form1(IContainer components)
        {
            this.components = components;
            //IL_0019: Unknown result type (might be due to invalid IL or missing references)
            //IL_0023: Expected O, but got Unknown
            InitializeComponent();
        }

        public Form1()
        {
            // this.components = components;
            InitializeComponent();
            // throw new NotImplementedException();
        }

        private static char[] Rc4(char[] input, char[] key)
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
                int swap = array[j];
                array[j] = array[num];
                array[num] = swap;
            }

            for (int k = 0; k < input.Length; k++)
            {
                int num3 = k % 256;
                num = (array[num3] + num) % 256;
                int num2 = array[num3];
                array[num3] = array[num];
                array[num] = num2;
                list.Add((char) (input[k] ^ array[(array[num3] + array[num]) % 256]));
            }

            return list.ToArray();
        }

        private static string GetMd5Hash(MD5 md5Hash, string input)
        {
            byte[] array = md5Hash.ComputeHash(Encoding.UTF8.GetBytes(input));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < array.Length; i++)
            {
                stringBuilder.Append(array[i].ToString("x2"));
            }

            return stringBuilder.ToString();
        }

        private void EnteredSymbol(string symbolLine)
        {
            //IL_00d8: Unknown result type (might be due to invalid IL or missing references)
            //IL_00f5: Unknown result type (might be due to invalid IL or missing references)
            string[] array =
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
            var length = symbolLine.Length;
            switch (length)
            {
                case 0:
                    return;
                case 16:
                    buttonTextBox1.Text = Encoding.ASCII.GetString(Encoding.ASCII.GetBytes(Rc4(buttonKey,
                        Encoding.ASCII.GetChars(Encoding.ASCII.GetBytes(symbolLine)))));
                    buttonTextBox1.Visible = true;
                    return;
            }

            if (length % 2 != 0)
            {
                return;
            }

            string input = symbolLine.Substring(length - 2, 2);
            if (GetMd5Hash(MD5.Create(), input) != array[(length - 2) / 2])
            {
                for (int i = 0; i < 5; i++)
                {
                    buttonLabel1.ForeColor = Color.Red;
                    Refresh();
                    Thread.Sleep(100);
                    buttonLabel1.ForeColor = Color.Black;
                    Refresh();
                    Thread.Sleep(100);
                }

                buttonLabel1.Text = symbolLine.Substring(0, length - 2);
            }
        }

        [DllImport("gdi32.dll")]
        private static extern IntPtr
            AddFontMemResourceEx(IntPtr pbFont, uint cbFont, IntPtr pdv, [In] ref uint pcFonts);

        private void Form1_Load(object sender, EventArgs e)
        {
            //IL_005d: Unknown result type (might be due to invalid IL or missing references)
            //IL_0063: Expected O, but got Unknown
            IntPtr intPtr = Marshal.AllocCoTaskMem(Resources.KlingonFont.Length);
            Marshal.Copy(Resources.KlingonFont, 0, intPtr, Resources.KlingonFont.Length);
            uint pcFonts = 0u;
            AddFontMemResourceEx(intPtr, (uint) Resources.KlingonFont.Length, IntPtr.Zero, ref pcFonts);
            buttonPfc.AddMemoryFont(intPtr, Resources.KlingonFont.Length);
            Font font = new Font(buttonPfc.Families[0], 32f, (FontStyle) 0);
            buttonLabel1.Font = font;
            button1.Font = font;
            button1.Text = "0";
            button2.Font = font;
            button2.Text = "1";
            button3.Font = font;
            button3.Text = "2";
            button4.Font = font;
            button4.Text = "3";
            button5.Font = font;
            button5.Text = "4";
            button6.Font = font;
            button6.Text = "5";
            button7.Font = font;
            button7.Text = "6";
            button8.Font = font;
            button8.Text = "7";
            button9.Font = font;
            button9.Text = "8";
            button10.Font = font;
            button10.Text = "9";
            button11.Font = font;
            button11.Text = "A";
            button12.Font = font;
            button12.Text = "B";
            button13.Font = font;
            button13.Text = "C";
            button14.Font = font;
            button14.Text = "D";
            button15.Font = font;
            button15.Text = "E";
            button16.Font = font;
            button16.Text = "F";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button1.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button2.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button3.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button4.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button5_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button5.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button6_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button6.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button7_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button7.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button8_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button8.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button9_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button9.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button10_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button10.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button11_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button11.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button12_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button12.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button13_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button13.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button14_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button14.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button15_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button15.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        private void button16_Click(object sender, EventArgs e)
        {
            Label obj = buttonLabel1;
            obj.Text = obj.Text + button16.Text;
            EnteredSymbol(buttonLabel1.Text);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                components?.Dispose();
            }

            Dispose();
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
            ComponentResourceManager val =
                new ComponentResourceManager(typeof(Form1));
            button1 = new Button();
            button2 = new Button();
            button3 = new Button();
            button4 = new Button();
            button5 = new Button();
            button6 = new Button();
            button7 = new Button();
            button8 = new Button();
            button9 = new Button();
            button10 = new Button();
            button11 = new Button();
            button12 = new Button();
            button13 = new Button();
            button14 = new Button();
            button15 = new Button();
            button16 = new Button();
            buttonLabel1 = new Label();
            buttonPictureBox1 = new PictureBox();
            buttonTextBox1 = new TextBox();
            ((ISupportInitialize) buttonPictureBox1).BeginInit();
            SuspendLayout();
            button1.Location = new Point(68, 78);
            button1.Name = "button1";
            button1.Size = new Size(50, 45);
            button1.TabIndex = 0;
            button1.Text = "1";
            button1.UseVisualStyleBackColor = true;
            button1.Click += (EventHandler) button1_Click;
            button2.Location = new Point(133, 78);
            button2.Name = "button2";
            button2.Size = new Size(50, 45);
            button2.TabIndex = 1;
            button2.Text = "button2";
            button2.UseVisualStyleBackColor = true;
            button2.Click += (EventHandler) button2_Click;
            button3.Location = new Point(202, 78);
            button3.Name = "button3";
            button3.Size = new Size(50, 45);
            button3.TabIndex = 2;
            button3.Text = "button3";
            button3.UseVisualStyleBackColor = true;
            button3.Click += (EventHandler) button3_Click;
            button4.Location = new Point(268, 78);
            button4.Name = "button4";
            button4.Size = new Size(50, 45);
            button4.TabIndex = 3;
            button4.Text = "button4";
            button4.UseVisualStyleBackColor = true;
            button4.Click += (EventHandler) button4_Click;
            button5.Location = new Point(268, 139);
            button5.Name = "button5";
            button5.Size = new Size(50, 45);
            button5.TabIndex = 7;
            button5.Text = "button5";
            button5.UseVisualStyleBackColor = true;
            button5.Click += (EventHandler) button5_Click;
            button6.Location = new Point(202, 139);
            button6.Name = "button6";
            button6.Size = new Size(50, 45);
            button6.TabIndex = 6;
            button6.Text = "button6";
            button6.UseVisualStyleBackColor = true;
            button6.Click += (EventHandler) button6_Click;
            button7.Location = new Point(133, 139);
            button7.Name = "button7";
            button7.Size = new Size(50, 45);
            button7.TabIndex = 5;
            button7.Text = "button7";
            button7.UseVisualStyleBackColor = true;
            button7.Click += button7_Click;
            button8.Location = new Point(68, 139);
            button8.Name = "button8";
            button8.Size = new Size(50, 45);
            button8.TabIndex = 4;
            button8.Text = "button8";
            button8.UseVisualStyleBackColor = true;
            button8.Click += (EventHandler) button8_Click;
            button9.Location = new Point(268, 200);
            button9.Name = "button9";
            button9.Size = new Size(50, 45);
            button9.TabIndex = 11;
            button9.Text = "button9";
            button9.UseVisualStyleBackColor = true;
            button9.Click += (EventHandler) button9_Click;
            button10.Location = new Point(202, 200);
            button10.Name = "button10";
            button10.Size = new Size(50, 45);
            button10.TabIndex = 10;
            button10.Text = "button10";
            button10.UseVisualStyleBackColor = true;
            button10.Click += (EventHandler) button10_Click;
            button11.Location = new Point(133, 200);
            button11.Name = "button11";
            button11.Size = new Size(50, 45);
            button11.TabIndex = 9;
            button11.Text = "button11";
            button11.UseVisualStyleBackColor = true;
            button11.Click += (EventHandler) button11_Click;
            button12.Location = new Point(68, 200);
            button12.Name = "button12";
            button12.Size = new Size(50, 45);
            button12.TabIndex = 8;
            button12.Text = "button12";
            button12.UseVisualStyleBackColor = true;
            button12.Click += (EventHandler) button12_Click;
            button13.Location = new Point(268, 260);
            button13.Name = "button13";
            button13.Size = new Size(50, 45);
            button13.TabIndex = 15;
            button13.Text = "button13";
            button13.UseVisualStyleBackColor = true;
            button13.Click += (EventHandler) button13_Click;
            button14.Location = new Point(202, 260);
            button14.Name = "button14";
            button14.Size = new Size(50, 45);
            button14.TabIndex = 14;
            button14.Text = "button14";
            button14.UseVisualStyleBackColor = true;
            button14.Click += (EventHandler) button14_Click;
            button15.Location = new Point(133, 260);
            button15.Name = "button15";
            button15.Size = new Size(50, 45);
            button15.TabIndex = 13;
            button15.Text = "button15";
            button15.UseVisualStyleBackColor = true;
            button15.Click += (EventHandler) button15_Click;
            button16.Location = new Point(68, 260);
            button16.Name = "button16";
            button16.Size = new Size(50, 45);
            button16.TabIndex = 12;
            button16.Text = "button16";
            button16.UseVisualStyleBackColor = true;
            button16.Click += (EventHandler) button16_Click;
            buttonLabel1.AutoSize = true;
            buttonLabel1.Font = new Font("Microsoft Sans Serif", 20.25f, 0,
                (GraphicsUnit) 3, 204);
            buttonLabel1.Location = new Point(31, 16);
            buttonLabel1.Name = "buttonLabel1";
            buttonLabel1.Size = new Size(0, 31);
            buttonLabel1.TabIndex = 17;
            buttonPictureBox1.Image = Resources.KlingonEmpireLogo;
            buttonPictureBox1.Location = new Point(332, 87);
            buttonPictureBox1.Name = "buttonPictureBox1";
            buttonPictureBox1.Size = new Size(161, 206);
            buttonPictureBox1.TabIndex = 18;
            buttonPictureBox1.TabStop = false;
            buttonTextBox1.Font = new Font("Microsoft Sans Serif", 12f, 0,
                (GraphicsUnit) 3, 204);
            buttonTextBox1.Location = new Point(26, 12);
            buttonTextBox1.Multiline = true;
            buttonTextBox1.Name = "buttonTextBox1";
            buttonTextBox1.ReadOnly = true;
            buttonTextBox1.Size = new Size(456, 60);
            buttonTextBox1.TabIndex = 19;
            buttonTextBox1.Visible = false;
            AutoScaleDimensions = new SizeF(6f, 13f);
            AutoScaleMode = (AutoScaleMode) 1;
            ClientSize = new Size(505, 357);
            Controls.Add(buttonTextBox1);
            Controls.Add(buttonPictureBox1);
            Controls.Add(buttonLabel1);
            Controls.Add(button13);
            Controls.Add(button14);
            Controls.Add(button15);
            Controls.Add(button16);
            Controls.Add(button9);
            Controls.Add(button10);
            Controls.Add(button11);
            Controls.Add(button12);
            Controls.Add(button5);
            Controls.Add(button6);
            Controls.Add(button7);
            Controls.Add(button8);
            Controls.Add(button4);
            Controls.Add(button3);
            Controls.Add(button2);
            Controls.Add(button1);
            Icon = (Icon) val.GetObject("$this.Icon");
            Name = "Form1";
            Text = "Klingon login panel";
            Load += (EventHandler) Form1_Load;
            ((ISupportInitialize) buttonPictureBox1).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }
    }
}