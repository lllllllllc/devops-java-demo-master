pipeline {

    agent any // 任何一个代理可用，在实际开发中，可能会配置多个 node 节点。

    options { // 可选项
        timestamps() // 显示时间
        skipDefaultCheckout() // 跳过从源代码控制中检出代码的默认情况
        disableConcurrentBuilds() // 禁止并行
        timeout(time: 1, unit: 'HOURS') // 超时时间设置为 1 小时
    }

    parameters { // 参数化构建
        gitParameter(name: 'tag', type: 'PT_TAG', defaultValue: 'origin/master', description: '基于 tag 进行项目构建')
    }

    environment { // 环境变量
        PROJECT_WS = "${WORKSPACE}" // 通用格式
    }

    stages { // 表示流水线的阶段，如：拉取代码、项目编译、代码质量检测、构建镜像、推送到 Harbor 仓库、通知目标服务器执行

        stage('项目编译') { // 表示流水线的具体阶段，如：拉取代码等
            // Jenkins 不配置任何环境的情况下，仅使用 Docker 兼容所有场景
            agent {
                docker {
                    image 'maven:3.9.0-eclipse-temurin-11-alpine'
                    args '-v $HOME/.m2:/root/.m2' // 将默认的 Maven 本地仓库挂载到宿主机上
                }
            }
            steps {
                echo '*** 项目编译 -- 开始 ***'
                sh "cd ${PROJECT_WS} && mvn clean package -Dmaven.test.skip=true"
                echo '*** 项目编译 -- 结束 ***'
            }
        }

        stage('代码质量检测') {
            agent {
                docker {
                    image 'sonarsource/sonar-scanner-cli'
                    args '-e SONAR_HOST_URL=http://192.168.1.14:9000'
                }
            }
            steps {
                echo '*** 代码质量检测 -- 开始 ***'
                sh "sonar-scanner \
                    -Dsonar.projectKey=${JOB_NAME} \
                    -Dsonar.sources=./ \
                    -Dsonar.login=admin \
                    -Dsonar.password=admin12345 \
                    -Dsonar.java.binaries=./target/"
                echo '*** 代码质量检测 -- 结束 ***'
            }
        }

        stage('构建镜像并推送到 Harbor 仓库中') {
            steps {
                // docker 构建镜像
                sh "docker build -t ${JOB_NAME}:$tag ."
                // docker 登录到 Harbor
                sh "docker login 192.168.68.16:80 -uadmin -pHarbor12345"
                // 将镜像重新打标签
                sh "docker tag ${JOB_NAME}:$tag 192.168.68.16:80/repo/${JOB_NAME}:$tag"
                // 推送镜像到 Harbor 仓库
                sh "docker push 192.168.68.16:80/repo/${JOB_NAME}:$tag"
            }
        }

        stage('通知目标服务器执行') {
            agent {
                docker {
                    image 'centos:7'
                }
            }
            steps {
                sh 'yum -y install sshpass && yum -y install openssh-clients'
                // 登录并执行操作
                sh 'sshpass -p123456 ssh -q -o StrictHostKeyChecking=no root@192.168.68.17 "yum -y install dnf && dnf -y update" '
                sh 'sshpass -p123456 ssh -q -o StrictHostKeyChecking=no root@192.168.68.17 "cd /root && sh /root/deploy.sh 192.168.68.16 80 repo ${JOB_NAME} 8089 $tag"'
            }
        }
    }

    post { // 后置动作

        always { // 总是执行，不管执行是否成功，都会执行该步骤
            echo '*** 检测 Docker 环境开始 ***'
            sh 'docker version'
            echo '*** 检测 Docker 环境结束 ***'
            echo '*** 检测 Git 环境开始 ***'
            sh 'git version'
            echo '*** 检测 Git 环境结束 ***'
            echo '*** 检测 Docker 环境开始 ***'
            sh 'docker version'
            echo '*** 检测 Docker 环境结束 ***'
            echo '*** 打印环境变量开始 ***'
            sh 'printenv'
            echo '*** 打印环境变量结束 ***'
            sh 'pwd && ls -lah'
            sh 'docker image prune -f'
        }

        success { // 后置执行成功执行，即所有阶段都执行成功，就会执行该步骤
            echo "后置执行 ---> 成功后执行..."

            dingtalk(
                    robot: 'jenkins-dingding',
                    type: 'MARKDOWN',
                    at: [],
                    atAll: false,
                    title: "${JOB_NAME}上线成功",
                    text: [" - 构建成功：${JOB_NAME} \n- 版本：${tag} \n- 持续时间：${currentBuild.durationString} "]
            )

        }

        failure { // 后置执行失败执行，即只要有阶段失败就会执行该步骤
            echo "后置执行 ---> 失败后执行..."

            dingtalk(
                    robot: 'jenkins-dingding',
                    type: 'MARKDOWN',
                    at: ["18551476116"],
                    atAll: false,
                    title: "${JOB_NAME}上线失败",
                    text: ["- 构建失败：${JOB_NAME} \\n- 版本：${tag} \\n- 持续时间：${currentBuild.durationString} "]
            )
        }

        aborted { // 取消后执行，用户点击取消后就会执行该步骤
            echo "后置执行 ---> 取消后执行..."
        }
    }

    
    
}

