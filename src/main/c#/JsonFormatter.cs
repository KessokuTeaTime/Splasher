using System;
using System.IO;

namespace Formatter
{
    class Program
    {
        static void Main(string[] args)
        {
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

					sw.WriteLine("	\"#1\": \"MIXIN TRANSLATIONS\",");
					sw.WriteLine("	\"mixin.splasher.x_mas\": \"Merry X-mas!\",");
					sw.WriteLine("	\"mixin.splasher.new_year\": \"Happy new year!\",");
					sw.WriteLine("	\"mixin.splasher.spooky\": \"OOoooOOOoooo! Spooky!\",");
					sw.WriteLine("	\"mixin.splasher.is_you\": \" IS YOU!\",");
					sw.Flush();

					sw.WriteLine("	\"#2\": \"BUILT-IN TRANSLATIONS\",");
					sw.Flush();

					sw.WriteLine("	\"#3\": \"SPLASH TEXTS\",");
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
						if (i != arrayList.Count - 1) sw.WriteLine("	\"splash." + i++ + "\": \"" + result + "\",");
						else sw.WriteLine("	\"splash." + i++ + "\": \"" + result + "\"");
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