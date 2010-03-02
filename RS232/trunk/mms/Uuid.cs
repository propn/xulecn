using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace mms
{
    class Uuid
    {
        public static string getUUID()
        {
            return System.Guid.NewGuid().ToString();

        }
    }
}
