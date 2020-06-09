ing-sw-2020-ferrara-franchini-fusco
===================================

Prova Finale di Ingengeria del Software
--------------------------------------------------------

![santorini](src/main/resources/images/santorini-logo.png)

## Contributors

- [Alessandro Ferrara](https://github.com/ferrohd)
- [Andrea Franchini](https://github.com/QUB3X)
- [Luigi Fusco](https://github.com/luigifusco)

## Implemented features

- Regole complete
- CLI
- GUI
- Socket
- 2 Advanced Features
    - Multiple matches
    - Advanced gods

## Project structure

Final version of the project is in `/delieveries/final/` directory.

## Usage

### Compiling the project

1. Make sure you have [Java](https://java.com/en/download) and [Maven](https://maven.apache.org/install.html) installed.

2. Clone this repo:

    ```shell
    git clone git@github.com:ferrohd/ing-sw-2020-ferrara-franchini-fusco.git
    ```

3. Install the required dependencies to the *local maven repository* by running this script:

    - Windows: `./install-maven-dependencies.bat`

    - Unix: `./install-maven-dependencies.sh`

4. Compile the project with *maven* using:

    ```shell
    mvn package
    ```

5. The two [jar](https://en.wikipedia.org/wiki/Jar_Jar_Binks) (*santorini-client.jar* and *santorini-server.jar*) are in the `/target` directory.

### Running the jars

Once you've acquired the *jar* files, either by compiling them yourself or downloading them from the `delieveries/final/jar/` directory on GitHub, you can run them the usual way:

- To start the server, use:
    ```shell
    java -jar santorini-server.jar
    ```

- To start the client, use:
    ```shell
    java -jar santorini-server.jar
    ```

#### Client options

If you need to change the client default server address or want to play with the CLI version, you have two options:

##### Using a settings file
By default, the client will look for a file called `settings.txt` in the same folder of the *jar* file. You can create the file yourself and copy the following settings.

```yml
interface: gui
hostname: 127.0.0.1
port: 42069
```

Replace `gui` with `cli` if you want to start the CLI insted of the GUI.

##### Using the flags
Otherwise, you can use the following flags to override the default configs or even the `settings.txt` file:

- `--gui` starts the GUI.

- `--cli` starts the CLI.
- `--address <address>` specify the server address (IP or URL).
- `--port <port>` speficy the server port (a number). Default one is `42069`.
- `--config <path to settings>` specify a custom path to a `settings.txt` file.

