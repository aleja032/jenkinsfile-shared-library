/**
 * Executes a full Django deployment, including setting up a virtual environment,
 * deploying files with rsync, and running Django management commands.
 *
 * @param webRoot The destination web root for deployment.
 * @param venvName The name of the virtual environment directory.
 */


// Función para configurar el entorno virtual y activar el entorno Python
def setUpEnv(webRoot, venvName) {
    sh """
        python3 -m venv ${webRoot}/${venvName} && \
        . ${webRoot}/${venvName}/bin/activate && \
        pip install -r ${webRoot}/requirements.txt
    """
}

// Función para hacer el despliegue con rsync
def deployingFiles(workspace, webRoot, venvName) {
    sh """
        rsync -rvD --delete \\
            --exclude '${venvName}/' \\
            --exclude '__pycache__/' \\
            --exclude '.git/' \\
            ${workspace}/ \\
            ${webRoot}/
    """
}

// Función para ejecutar comandos de Django dentro del entorno virtual
def executeCommandsDjango(webRoot) {
    sh """
        . ${webRoot}/venv/bin/activate && \
        python manage.py migrate collectstatic --no-input
    """
}

// Función para limpiar el entorno virtual
def cleanEnv(webRoot, venvName) {
    sh "rm -rf ${webRoot}/${venvName}"
}


