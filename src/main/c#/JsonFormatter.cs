using System;
using System.IO;

namespace Formatter
{
    class Program
    {
        public static String MOD_ID = "splasher";

		static String format(int key, String content) {
			return "	\"splash.minecraft." + key + "\": \"" + content + "\"";
		}

		static String formatFestival(String key, String content) {
			return "	\"festival." + MOD_ID + "." + key + "\": \"" + content + "\"";
		}

        static void Main(string[] args)
        {
            try
            {
				List<String> arrayList = new List<String>();
                using (StreamReader sr = new StreamReader("splashes.txt"))
                {
					String? line;
                    while ((line = sr.ReadLine()) != null)
                    {
						arrayList.Add(line);
					}
				}
				using StreamWriter sw = new StreamWriter("en_us.json", false);
				{
					sw.AutoFlush = true;
					sw.WriteLine("{");

					sw.WriteLine(formatFestival("x_mas", "Merry X-mas!") + ",");
					sw.WriteLine(formatFestival("new_year", "Happy new year!") + ",");
					sw.WriteLine(formatFestival("halloween", "OOoooOOOoooo! Spooky!") + ",");
					sw.WriteLine(formatFestival("is_you", "IS YOU!") + ",");

					sw.WriteLine("");

					int i = 0;
					foreach(String s in arrayList)
					{
						String result = s;
						int k = 0;
						foreach (char t in result)
						{
							if (t == '"' || t == '\\')
							{
								result = result.Substring(0, k) + '\\' + result.Substring(k, result.Length - k);
								k++;
							}
							k++;
						}
						Console.WriteLine("Writing Line " + i + ": " + result);
						sw.WriteLine(format(i++, result) + (i < arrayList.Count ? "," : ""));
					}
					sw.WriteLine("}");
					sw.Close();
				}
            }
            catch (Exception e)
            {
                Console.WriteLine("File Not Supported: " + e.Message);
            }
            Console.ReadKey();
        }
    }
}