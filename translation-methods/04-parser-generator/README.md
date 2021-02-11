## **King Grammar Parser Generator**

### King Grammar tool is LL(1) grammar Java Parser Generator.

#### **How to write own rules**?

- **Grammar name**:
    - All **.king** grammar files must have grammar name. To define grammar name you should declare grammar line using
      **grammar** keyword and **grammar name which is _capitalized_**

      Example:
      ```java 
      grammar Python
      ```

- **Arrow sign**:
    - => this sign is used to start sub-rules declaration

      Example:
      ```java
      WS => "[\s]+"
      ```

- **Package name**:
    - **Package rule is required!**
    - **Package rule structure:**
      ```java
      <package PACKAGE_NAME>
      ```    
    - **PACKAGE_NAME:** ```lowercased word```

      Example:
      ```java
      <package gen.python.definition>
      ```

- **Skip rules**:
    - **Skip rule structure**

      ```java
      skip SKIP_RULE_NAME => "regex_pattern"
      ```

        - **SKIP_RULE_NAME:** ```[A-Z_]+```

          **Example:**
          ```java
          skip WS => "[\n\t\s]"
          ```

- **Token rules**:
    - **Token rule structure:**
      ```java
      TOKEN_NAME (INHERITED_ATTRIBUTES)? => (SYNTHESIZED_ATTRIBUTES)?
      TOKEN_REGEX { CODE? }
      ```
    - ***? - means that value can be skipped***
    - **TOKEN_NAME**: ```[A-Z_]+```
    - **INHERITED_ATTRIBUTES**: ```(type name)+```
    - **SYNTHESIZED_ATTRIBUTES**: ```(type name)+```
    - **TOKEN_REGEX**: ```"regex_pattern"```
    - **CODE**:    
      Synthesized attributes accessing for current rule should be prefixed with
      ***@*** or ***node keyword***
      
      Example:
      ```java 
      NUM => (int value)
         "[0-9]+" { @value     = Integer.parseInt(@text); }
                  { node.value = Integer.parseInt(@text); }
      ```   
      Also in code block can be written **Java 1.8+ valid code**
    
      **Example:**

      ```java 
      NUMBER => (int numberValue, String log)
      { @log = "number parsed"; @numberValue = Intger.parseInt(@text); @numberValue += 239; }
      ```
- **Non terminal rules**;
    - **Non terminal rule structure**
      ```java
      NON_TERMINAL_NAME (INHERITED_ATTRIBUTES)? => (SYNTHESIZED_ATTRIBUTES)?
          sub-rules { CODE? }   
      ( | sub-rules { CODE? } )* - more sub-rules
      ```
    - **NON_TERMINAL_NAME:** ```lowercased word```
    - **INHERITED_ATTRIBUTES:** ```Same as for tokens```
    - **SYNTHESIZED_ATTRIBUTES:** ```Same as for tokens```
    - **CODE:** ```Same as for tokens```  
    - **sub-rules:**
       Line of **tokens** or **non-terminal** rules
       **parents** or **left child** fields can be passed as params for an **inherited** attribute for current sub-rule tree.
      
       **Example:**
       ```java
       addition(int acc) => (int value)
          PLUS NUM(acc) { @value = NUM.value; }
        | EPS           { @value = acc;       }    
       ``` 
    - **EPS:** Is **keyword** used for **epsilon** rule 
      
      **Example:**
      ```java  
         num => (int value)
              DIGITS  { @value = DIGITS.number }
            | EPSE    { @value = 0;            }
      ```  
    
    
### Python function definition grammar:

```python
@Decorator
def main(x, y, z)
```

**Grammar example:**

```java
grammar Python

<package python>

start => (String str, int n)
  decorator definition
    { @str = decorator.str; @str = @str + definition.str; @n = definition.n; }

decorator => (String str)
    AT ID
      { @str = AT.text; @str = @str + ID.text + "\n"; }
  | EPS
      { @str = ""; }

definition => (String str, int n)
  DEF fun
    { @str = DEF.text; @str = @str + " " + fun.str; @n = fun.n; }

fun => (String str, int n)
  ID L_BRACE args R_BRACE
    { @str = ID.text; @str = @str + "(" + args.str + ")"; @n = args.n; }

args => (String str, int n)
    ID restArgs
      { @str = ID.text; @str = @str + restArgs.str; @n = 1 + restArgs.n; }
  | EPS
      { @str = new String(); @n = 0; }

restArgs => (String str, int n)
    COMMA args
      { @str = ", "; @str = @str + args.str; @n = args.n; }
  | EPS
      { @str = new String(); @n = 0; }

AT      => "@"
COMMA   => ","
L_BRACE => "\("
R_BRACE => "\)"
DEF     => "def"
ID      => "[a-zA-Z]+"

skip WS => "[\s\n\t]"
```
