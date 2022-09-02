using System;
using System.IO;

namespace Formatter
{
    class Program
    {
        static void Main(string[] args)
        {
            String MOD_ID = "splasher";

            try
            {
				List<String> arrayList = new List<String>();
                using (StreamReader sr = new StreamReader("splashes.txt"))
                {
					String line;
                    while ((line = sr.ReadLine()) != null)
                    {
						arrayList.Add(line);
					}
				}	
				using StreamWriter sw = new StreamWriter("en_us.json", false);
				{
					sw.WriteLine("{");
					sw.Flush();

					sw.WriteLine("	\"mixin." + MOD_ID + ".x_mas\": \"Merry X-mas!\",");
					sw.WriteLine("	\"mixin." + MOD_ID + ".new_year\": \"Happy new year!\",");
					sw.WriteLine("	\"mixin." + MOD_ID + ".halloween\": \"OOoooOOOoooo! Spooky!\",");
					sw.WriteLine("	\"mixin." + MOD_ID + ".is_you\": \" IS YOU!\",\r\n\r\n\r\n");
					sw.Flush();

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
						if (i != arrayList.Count - 1) sw.WriteLine("    \"splash.minecraft." + i++ + "\": \"" + result + "\",");
						else sw.WriteLine("    \"splash.minecraft." + i++ + "\": \"" + result + "\"");
						sw.Flush();
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