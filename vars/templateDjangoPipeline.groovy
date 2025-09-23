def call(Map params = [:]) {
    pipeline {
        agent any
        environment {
            VENV = params.get('venv', 'venv')
            REQUIREMENTS = params.get('requirements', 'requirements.txt')
            PROJECT_DIR = params.get('projectDir', '.')
        }
        stages {
            stage('Preparar entorno') {
                steps {
                    script {
                        sh "python -m venv ${env.VENV}"
                        sh "${env.VENV}/Scripts/activate && pip install -r ${env.PROJECT_DIR}/${env.REQUIREMENTS}"
                    }
                }
            }
        }
        post {
            always {
                echo "YEIIII"
            }
        }
    }
}