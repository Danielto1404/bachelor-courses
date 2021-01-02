using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Runtime.CompilerServices;

namespace XenoLogin.XenoLogin.Properties
{
	[GeneratedCode("System.Resources.Tools.StronglyTypedResourceBuilder", "4.0.0.0")]
	[DebuggerNonUserCode]
	[CompilerGenerated]
	internal class Resources
	{
		private static ResourceManager _resourceMan;

		private static CultureInfo ResourceCulture;

		[EditorBrowsable(EditorBrowsableState.Advanced)]
		private static ResourceManager ResourceManager
		{
			get
			{
				if (_resourceMan == null)
				{
					_resourceMan = new ResourceManager("XenoLogin.Properties.Resources", typeof(Resources).Assembly);
				}
				return _resourceMan;
			}
		}

		internal static Bitmap KlingonEmpireLogo => (Bitmap)ResourceManager.GetObject("Klingon_Empire_logo", ResourceCulture);

		internal static byte[] KlingonFont => (byte[])ResourceManager.GetObject("klingon_font", ResourceCulture);
	}
}
