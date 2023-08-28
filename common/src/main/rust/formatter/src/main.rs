use std::{fs::OpenOptions, path::Path};

mod info {
    pub const MOD_ID: &'static str = "splasher";
    pub const SOURCE_FILE_PATH: &'static str = "src/1.19/splashes.txt";
    pub const TARGET_FILE_PATH: &'static str = "src/1.19/en_us.json";

    pub const FESTIVALS: [Festival; 4] = [
        Festival {
            key: "x_mas",
            content: "Merry X-mas!",
        },
        Festival {
            key: "new_year",
            content: "Happy new year!",
        },
        Festival {
            key: "halloween",
            content: "OOoooOOOOooo! Spooky!",
        },
        Festival {
            key: "is_you",
            content: "%s IS YOU",
        },
    ];

    pub struct Festival {
        pub key: &'static str,
        pub content: &'static str,
    }
}

fn main() {
    use {info::*, std::*};

    let file_in = fs::File::open(SOURCE_FILE_PATH)
        .expect(&format!("Unable to read file '{}'", SOURCE_FILE_PATH));

    let parent = Path::new(TARGET_FILE_PATH).parent().unwrap();
    if let Ok(_) = fs::create_dir_all(parent) {
        let file_out = OpenOptions::new()
            .write(true)
            .create(true)
			.truncate(true)
            .open(TARGET_FILE_PATH)
            .expect(&format!("Unable to write to file '{}'", TARGET_FILE_PATH));

        formatter::format(file_in, file_out);
    } else {
        panic!("Unable to create directory '{}'", parent.display());
    }
}

mod formatter {
    use std::{
        fs::File,
        io::BufReader,
        io::{BufRead, Write},
    };

    fn validate_line(line: &str) -> String {
        let mut line = line.to_string();
        line = line
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
            .replace("\n", "\\n");
        line
    }

    pub fn format(file_in: File, mut file_out: File) {
        use crate::info::*;

        let lines = BufReader::new(file_in).lines();

        let mut json = String::new();
        let mut line_index = 0;
        json.push_str("{\n");

        for festival in FESTIVALS.iter() {
            json.push_str(&format!(
                "	\"{}.{}.{}\": \"{}\",\n",
                "festival",
                MOD_ID,
                festival.key,
                validate_line(festival.content)
            ));
        }

        json.push_str("\n");

        for line in lines {
            let line = validate_line(line.unwrap().as_str());
            let line = line.trim();

            if line.is_empty() {
                continue;
            }

            json.push_str(&format!(
                "	\"{}.{}.{}\": \"{}\",\n",
                "splash", "minecraft", line_index, line
            ));
            line_index += 1;
        }

        json.pop();
        json.pop();
        json.push_str("\n}");

        file_out.write(json.as_bytes()).unwrap();
    }
}
