use std::io;

fn main() {
    let mut input = String::new();

    io::stdin().read_line(&mut input).expect("Error while reading");
    let checked = input.trim();
    if checked.len() == 0 {
        println!("???");
        return;
    }
    let check:Vec<u32> = vec![215, 233, 200, 218, 374, 167, 164, 158, 167, 311, 308, 296, 158, 164, 155, 167, 170, 173, 173, 167, 161, 158, 155, 152, 158, 164, 311, 311, 308, 380];
    let mut counter = 0;
    let len = check.len();
    for i in checked.as_bytes() {
        if counter >= len || ((*i as u32) * 3 + 5) != check[counter] {
            println!("Nope");
            return;
        }
        counter+=1;
    }

    println!("Well done!");

}

y = [chr((x - 5) // 3) for x in [215, 233, 200, 218, 374, 167, 164, 158, 167, 311, 308, 296, 158, 164, 155, 167, 170, 173, 173, 167, 161, 158, 155, 152, 158, 164, 311, 311, 308, 380]]

res = ""
for c in y:
  res += c

print(res)

# Answer:= FLAG{6536fea35267886432135ffe}
