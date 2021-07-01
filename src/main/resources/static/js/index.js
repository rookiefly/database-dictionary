var vue = new Vue({
    el: '#app',
    data: {
        value: new Date(),
        loading: false,
        iconUrl: ctx + "/assetss/images/fav.png",
        iconTitle: "database-dictionary",
        iconNotes: "数据库表结构字典工具",
        o_ip: '',
        o_port: "1521",
        o_schema: "orcl",
        o_username: "",
        o_password: "",
        o_filepath: "D://",
        m_ip: '',
        m_port: "3306",
        m_schema: "",
        m_username: "",
        m_password: "",
        m_filepath: "D://",
        s_ip: '',
        s_port: "1433",
        s_schema: "",
        s_username: "",
        s_password: "",
        s_filepath: "D://",
        welcomeDivShow: true,
        oracleDivShow: false,
        mysqlDivShow: false,
        sqlserverDivShow: false,
        oraclePopoverVisible: false,
        mysqlPopoverVisible: false,
        sqlserverPopoverVisible: false,
        welcomeIconImg: ctx + "/assetss/images/welcome-icon-click.png",
        oracleIconImg: ctx + "/assetss/images/oracle-icon-unclick.png",
        mysqlIconImg: ctx + "/assetss/images/mysql-icon-unclick.png",
        sqlserverIconImg: ctx + "/assetss/images/sqlserver-icon-unclick.png",
        githubIconImg: ctx + "/assetss/images/GitHub-icon.png",
        emailIconImg: ctx + "/assetss/images/email-icon.png",
        welcomeBarStyle: {
            borderBottom: "1px solid rgb(235, 233, 233)",
            background: "#F2F6FC"
        },
        oracleBarStyle: {
            borderBottom: "1px solid rgb(235, 233, 233)",
            background: "rgb(255,255,255)"
        },
        mysqlBarStyle: {
            borderRight: "1px solid rgb(235, 233, 233)",
            background: "rgb(255,255,255)"
        },
        sqlServerBarStyle: {
            borderTop: "1px solid rgb(235, 233, 233)",
            background: "rgb(255,255,255)"
        },
        contentStyle: {
            background: "#F2F6FC",
            width: "100%",
            height: "100%"
        }
    },
    methods: {
        generateWord(dialect, exportFileType) {
            let ip;
            let port;
            let schema;
            let username;
            let password;
            if (exportFileType == undefined || exportFileType == '') {
                this.$message.error('导出文件类型不能为空');
                return;
            }
            if (dialect == 'oracle') {
                this.oraclePopoverVisible = false;
                ip = this.o_ip;
                port = this.o_port;
                schema = this.o_schema;
                username = this.o_username;
                password = this.o_password;
            } else if (dialect == 'mysql') {
                this.mysqlPopoverVisible = false;
                ip = this.m_ip;
                port = this.m_port;
                schema = this.m_schema;
                username = this.m_username;
                password = this.m_password;
            } else if (dialect == 'sqlserver') {
                this.sqlserverPopoverVisible = false;
                ip = this.s_ip;
                port = this.s_port;
                schema = this.s_schema;
                username = this.s_username;
                password = this.s_password;
            }

            if (ip == '') {
                this.$message.error('IP不能为空');
                return;
            }
            if (port == '') {
                this.$message.error('端口不能为空');
                return;
            }
            if (schema == '') {
                this.$message.error('实例/数据库名称不能为空');
                return;
            }
            if (username == '') {
                this.$message.error('用户名不能为空');
                return;
            }
            if (password == '') {
                this.$message.error('密码不能为空');
                return;
            }
            if (exportFileType == 'markdown') {
                window.open("/export/dictionary.md?dialect=" + dialect + "&host=" + ip + "&port=" + port + "&schema=" + schema + "&user=" + username + "&password=" + password + "&exportFileType=" + exportFileType);
            } else if (exportFileType == 'excel') {
                window.open("/dbExport/makeExcel?dialect=" + dialect + "&host=" + ip + "&port=" + port + "&schema=" + schema + "&user=" + username + "&password=" + password + "&exportFileType=" + exportFileType);
            } else if (exportFileType == 'word') {
                window.open("/dbExport/makeWord?dialect=" + dialect + "&host=" + ip + "&port=" + port + "&schema=" + schema + "&user=" + username + "&password=" + password + "&exportFileType=" + exportFileType);
            }

        },
        barClick(dialect) {
            if (dialect == 'welcome') {
                this.welcomeDivShow = true;
                this.oracleDivShow = false;
                this.mysqlDivShow = false;
                this.sqlserverDivShow = false;
                this.welcomeBarStyle.background = "#F2F6FC";
                this.mysqlBarStyle.background = "rgb(255,255,255)";
                this.sqlServerBarStyle.background = "rgb(255,255,255)";
                this.oracleBarStyle.background = "rgb(255,255,255)";
                //图片
                this.welcomeIconImg = ctx + "/assetss/images/welcome-icon-click.png";
                this.oracleIconImg = ctx + "/assetss/images/oracle-icon-unclick.png";
                this.mysqlIconImg = ctx + "/assetss/images/mysql-icon-unclick.png";
                this.sqlserverIconImg = ctx + "/assetss/images/sqlserver-icon-unclick.png";
            } else if (dialect == 'oracle') {
                this.welcomeDivShow = false;
                this.oracleDivShow = true;
                this.mysqlDivShow = false;
                this.sqlserverDivShow = false;
                this.welcomeBarStyle.background = "rgb(255,255,255)";
                this.mysqlBarStyle.background = "rgb(255,255,255)";
                this.sqlServerBarStyle.background = "rgb(255,255,255)";
                this.oracleBarStyle.background = "#F2F6FC";
                //图片
                this.welcomeIconImg = ctx + "/assetss/images/welcome-icon-unclick.png";
                this.oracleIconImg = ctx + "/assetss/images/oracle-icon-click.png";
                this.mysqlIconImg = ctx + "/assetss/images/mysql-icon-unclick.png";
                this.sqlserverIconImg = ctx + "/assetss/images/sqlserver-icon-unclick.png";
            } else if (dialect == 'mysql') {

                this.welcomeDivShow = false;
                this.oracleDivShow = false;
                this.mysqlDivShow = true;
                this.sqlserverDivShow = false;
                this.mysqlBarStyle.background = "#F2F6FC";
                this.welcomeBarStyle.background = "rgb(255,255,255)";
                this.sqlServerBarStyle.background = "rgb(255,255,255)";
                this.oracleBarStyle.background = "rgb(255,255,255)";
                //图片
                this.welcomeIconImg = ctx + "/assetss/images/welcome-icon-unclick.png";
                this.oracleIconImg = ctx + "/assetss/images/oracle-icon-unclick.png";
                this.mysqlIconImg = ctx + "/assetss/images/mysql-icon-click.png";
                this.sqlserverIconImg = ctx + "/assetss/images/sqlserver-icon-unclick.png";
            } else if (dialect == 'sqlserver') {
                this.welcomeDivShow = false;
                this.oracleDivShow = false;
                this.mysqlDivShow = false;
                this.sqlserverDivShow = true;
                this.welcomeBarStyle.background = "rgb(255,255,255)";
                this.mysqlBarStyle.background = "rgb(255,255,255)";
                this.oracleBarStyle.background = "rgb(255,255,255)";
                this.sqlServerBarStyle.background = "#F2F6FC";
                //图片
                this.welcomeIconImg = ctx + "/assetss/images/welcome-icon-unclick.png";
                this.oracleIconImg = ctx + "/assetss/images/oracle-icon-unclick.png";
                this.mysqlIconImg = ctx + "/assetss/images/mysql-icon-unclick.png";
                this.sqlserverIconImg = ctx + "/assetss/images/sqlserver-icon-click.png";

            }
            this.contentStyle.background = "#F2F6FC";

        },
        generateDocHtml(dialect) {
            let ip;
            let port;
            let schema;
            let username;
            let password;
            if (dialect == 'oracle') {
                ip = this.o_ip;
                port = this.o_port;
                schema = this.o_schema;
                username = this.o_username;
                password = this.o_password;
                //filepath = this.o_filepath;
            } else if (dialect == 'mysql') {
                console.log(this.m_ip);
                ip = this.m_ip;
                port = this.m_port;
                schema = this.m_schema;
                username = this.m_username;
                password = this.m_password;
            } else if (dialect == 'sqlserver') {
                ip = this.s_ip;
                port = this.s_port;
                schema = this.s_schema;
                username = this.s_username;
                password = this.s_password;
            }

            if (ip == '') {
                this.$message.error('IP不能为空');
                return;
            }
            if (port == '') {
                this.$message.error('端口不能为空');
                return;
            }
            if (schema == '') {
                this.$message.error('实例/数据库名称不能为空');
                return;
            }
            if (username == '') {
                this.$message.error('用户名不能为空');
                return;
            }
            if (password == '') {
                this.$message.error('密码不能为空');
                return;
            }
            window.open("/view/dictionary.html?dialect=" + dialect + "&host=" + ip + "&port=" + port + "&schema=" + schema + "&user=" + username + "&password=" + password);
        }
    }
});