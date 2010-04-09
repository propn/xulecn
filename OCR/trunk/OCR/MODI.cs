using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace OCR
{
    /// <summary>
    /// Representation for the MODI OCR parameters
    /// OCR 参数
    /// 
    /// </summary>
    public class MODIOCRParameters
    {
        private MODI.MiLANGUAGES _language = MODI.MiLANGUAGES.miLANG_CHINESE_SIMPLIFIED;//语音默认为中文

        public MODI.MiLANGUAGES Language
        {
            get { return _language; }
            set { _language = value; }
        }
        private bool _withAutoRotation = true;
        public bool WithAutoRotation
        {
            get { return _withAutoRotation; }
            set { _withAutoRotation = value; }
        }
        private bool _WithStraightenImage = true;
        public bool WithStraightenImage
        {
            get { return _WithStraightenImage; }
            set { _WithStraightenImage = value; }
        }
    }
}
