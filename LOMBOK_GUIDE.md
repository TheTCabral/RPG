# 📚 Guia Lombok - Entendendo o Projeto RPG

## ✅ O que foi configurado:

### 1. **Anotações do Lombok na classe `Personagem`**

```java
@Getter
@Setter
@ToString(exclude = {"vidaAtual", "vidaMaxima"})
@EqualsAndHashCode(exclude = {"vidaAtual", "vidaMaxima"})
public abstract class Personagem {
```

### 2. **Explicação de cada anotação:**

#### `@Getter`
- **O que faz:** Gera automaticamente os métodos `get` para todos os atributos
- **Exemplo:** Cria `getNome()`, `getVidaAtual()`, etc.
- **Boilerplate removido:** ~50 linhas de código

#### `@Setter`
- **O que faz:** Gera automaticamente os métodos `set` para todos os atributos
- **Exceção:** Os setters customizados com validação (setVidaAtual, setDefesa, setAtaque) funcionam normalmente

#### `@ToString`
- **O que faz:** Gera automaticamente o método `toString()` com todos os atributos
- **`exclude`:** Exclui a vida e vidaMaxima da representação (para não poluir a saída)

#### `@EqualsAndHashCode`
- **O que faz:** Gera automaticamente `equals()` e `hashCode()` baseado nos atributos
- **`exclude`:** Exclui vida do cálculo (pois a vida muda constantemente)

---

## 🔧 Configurações do Maven (pom.xml)

Seu `pom.xml` já está configurado com:

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.32</version>
    <scope>provided</scope>
</dependency>
```

E o plugin de annotation processing:

```xml
<annotationProcessorPaths>
    <path>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>${lombok.version}</version>
    </path>
</annotationProcessorPaths>
```

---

## 🎯 Explicação: `Math.min()` e `Math.max()`

### **Math.min(a, b)** - Retorna o MENOR valor
```java
vidaAtual = Math.min(vidaAtual, vidaMaxima);
// Se vidaAtual = 150 e vidaMaxima = 100
// Resultado: vidaAtual = 100 ✅ (vida não ultrapassa o máximo)
```

### **Math.max(a, b)** - Retorna o MAIOR valor
```java
vidaAtual = Math.max(0, vidaAtual - danoReal);
// Se resultado da subtração = -50
// Resultado: vidaAtual = 0 ✅ (vida nunca fica negativa)

defesa = Math.max(0, defesa);
// Garante que defesa nunca é negativa
```

### **Combinado (Math.min com Math.max)**
```java
this.vidaAtual = Math.max(0, Math.min(vida, vidaMaxima));
// 1. Math.min(vida, vidaMaxima) - garante que não ultrapassa máximo
// 2. Math.max(0, resultado) - garante que não fica negativa
// Resultado: vida sempre entre 0 e vidaMaxima ✅
```

---

## 🔐 Por que atributos são `protected`?

### **public** ❌ NÃO use em RPG
```java
public int vidaAtual; // Qualquer classe pode acessar/modificar!
personagem.vidaAtual = -9999; // ❌ Sem controle!
```

### **private** ✅ Mas precisa de getters/setters
```java
private int vidaAtual; // Só a classe acessa
// Precisa gerar getters/setters para cada atributo 😫
```

### **protected** ✅ MELHOR para herança
```java
protected int vidaAtual; // Classe e subclasses acessam
// Subclasses como Guerreiro, Mago podem acessar
// Mas classes externas precisam de getters/setters
```

### **Por que com Lombok fica melhor?**
```java
@Getter
@Setter
protected int vidaAtual; // Lombok cria getNome() automaticamente!
```

---

## 🎁 Por que usar Getters e Setters?

### ❌ Sem getters/setters (acesso direto)
```java
personagem.vidaAtual = -50; // ❌ PROBLEMA!
// A vida fica negativa, quebrando as regras do jogo
```

### ✅ Com getters/setters (com validação)
```java
personagem.setVidaAtual(-50);
// Internamente:
// vidaAtual = Math.max(0, Math.min(-50, vidaMaxima));
// Resultado: vidaAtual = 0 ✅ CORRETO!
```

### **Benefícios:**
1. **Encapsulamento** - Controla como os dados são modificados
2. **Validação** - Força as regras de negócio
3. **Flexibilidade** - Pode adicionar lógica depois sem quebrar código
4. **Segurança** - Impede valores inválidos

---

## 📝 Como criar POM.XML (desde o início)

### **Método 1: Manual**

Crie um arquivo `pom.xml` na raiz do projeto:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <!-- Identificação do projeto -->
    <groupId>com.rpg</groupId>
    <artifactId>rpgzinho</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>RPGzinho</name>
    
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <lombok.version>1.18.32</lombok.version>
    </properties>
    
    <dependencies>
        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>${lombok.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### **Método 2: Usando Maven Archetype**
```bash
mvn archetype:generate -DgroupId=com.rpg -DartifactId=rpgzinho -DarchetypeArtifactId=maven-archetype-quickstart
```

### **Método 3: IntelliJ IDE**
1. File → New → Project
2. Selecione "Maven" como builder
3. Configure GroupId, ArtifactId, Version
4. IDE cria o pom.xml automaticamente

---

## 🚀 Configurar Maven em um projeto sem Maven

### **Passo 1: Instalar Maven**
- Windows: Download em https://maven.apache.org/download.cgi
- Extraia em `C:\Program Files\maven`
- Adicione `C:\Program Files\maven\bin` às variáveis de ambiente

### **Passo 2: Verificar instalação**
```bash
mvn --version
```

### **Passo 3: Criar estrutura de pastas**
```
rpg-project/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/rpg/
│   └── test/
│       └── java/
```

### **Passo 4: Adicionar pom.xml** (copie do exemplo acima)

### **Passo 5: Compilar**
```bash
mvn clean compile
```

---

## 🔍 Habilitar Lombok no IntelliJ IDEA

### **Passo 1: Instalar Plugin**
1. File → Settings → Plugins
2. Procure "Lombok"
3. Clique "Install"
4. Reinicie o IDE

### **Passo 2: Ativar Annotation Processing**
1. File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors
2. Marque ✅ "Enable annotation processing"

### **Passo 3: Invalidar Cache**
1. File → Invalidate Caches...
2. Selecione "Invalidate and Restart"

### **Passo 4: Resultado**
Agora o IntelliJ reconhecerá todos os getters/setters gerados pelo Lombok! 🎉

---

## ✨ Resumo das Anotações do Lombok

| Anotação | O que faz | Exemplo |
|----------|----------|---------|
| `@Getter` | Cria `get` para cada atributo | `getNome()` |
| `@Setter` | Cria `set` para cada atributo | `setVidaAtual()` |
| `@ToString` | Cria método `toString()` | `Personagem(nome=Hero, ataque=10)` |
| `@EqualsAndHashCode` | Cria `equals()` e `hashCode()` | Comparar personagens |
| `@Data` | Tudo acima + mais | ⚠️ Não use em classes com lógica complexa |
| `@AllArgsConstructor` | Construtor com todos os atributos | |
| `@NoArgsConstructor` | Construtor vazio | Já tem na classe |
| `@Builder` | Pattern Builder | `Personagem.builder().nome("Hero").build()` |

---

## 🎮 Regras de Negócio Implementadas

✅ **Vida nunca pode ser menor que zero**
```java
vidaAtual = Math.max(0, vidaAtual - danoReal);
```

✅ **Vida nunca pode ultrapassar o máximo**
```java
vidaAtual = Math.min(vidaAtual, vidaMaxima);
```

✅ **Ataque e defesa são sempre positivos**
```java
ataque = Math.max(0, ataque);
defesa = Math.max(0, defesa);
```

✅ **Personagem morto não pode executar ações**
```java
public void atacar() {
    if (!estaVivo()) return; // Impede ação
}
```

---

## 🏆 Próximos Passos

1. Aplique `@Getter @Setter` em outras classes do projeto
2. Use `@AllArgsConstructor` para simplificar construtores
3. Considere `@Builder` para criar personagens complexos
4. Mantenha a validação em setters customizados

Parabéns! 🎉 Seu projeto RPG agora usa Lombok corretamente!

