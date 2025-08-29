package com.hqumath.demo.bean;

import java.util.List;

public class CommitEntity {

    /**
     * sha : 11f274bacfc064fe04e235b8bb9d6be4a09557f1
     * node_id : MDY6Q29tbWl0MTg5NTU2NTY2OjExZjI3NGJhY2ZjMDY0ZmUwNGUyMzViOGJiOWQ2YmU0YTA5NTU3ZjE=
     * commit : {"author":{"name":"gyd","email":"guoyadi@live.cn","date":"2019-07-26T17:11:37Z"},"committer":{"name":"gyd","email":"guoyadi@live.cn","date":"2019-07-26T17:11:37Z"},"message":"toolbar 代替actionbar","tree":{"sha":"20efa7fb76f8283b21f07ca23d230190bb74401f","url":"https://api.github.com/repos/Ninja2005/AndroidMVVM/git/trees/20efa7fb76f8283b21f07ca23d230190bb74401f"},"url":"https://api.github.com/repos/Ninja2005/AndroidMVVM/git/commits/11f274bacfc064fe04e235b8bb9d6be4a09557f1","comment_count":0,"verification":{"verified":false,"reason":"unsigned","signature":null,"payload":null}}
     * url : https://api.github.com/repos/Ninja2005/AndroidMVVM/commits/11f274bacfc064fe04e235b8bb9d6be4a09557f1
     * html_url : https://github.com/Ninja2005/AndroidMVVM/commit/11f274bacfc064fe04e235b8bb9d6be4a09557f1
     * comments_url : https://api.github.com/repos/Ninja2005/AndroidMVVM/commits/11f274bacfc064fe04e235b8bb9d6be4a09557f1/comments
     * author : null
     * committer : null
     * parents : [{"sha":"246f815cf91770bbb090757c19b26c4e50b73a83","url":"https://api.github.com/repos/Ninja2005/AndroidMVVM/commits/246f815cf91770bbb090757c19b26c4e50b73a83","html_url":"https://github.com/Ninja2005/AndroidMVVM/commit/246f815cf91770bbb090757c19b26c4e50b73a83"}]
     */

    private String sha;
    private String node_id;
    private CommitBean commit;
    private String url;
    private String html_url;
    private String comments_url;
    private Object author;
    private Object committer;
    private List<ParentsBean> parents;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public CommitBean getCommit() {
        return commit;
    }

    public void setCommit(CommitBean commit) {
        this.commit = commit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public Object getAuthor() {
        return author;
    }

    public void setAuthor(Object author) {
        this.author = author;
    }

    public Object getCommitter() {
        return committer;
    }

    public void setCommitter(Object committer) {
        this.committer = committer;
    }

    public List<ParentsBean> getParents() {
        return parents;
    }

    public void setParents(List<ParentsBean> parents) {
        this.parents = parents;
    }

    public static class CommitBean {
        /**
         * author : {"name":"gyd","email":"guoyadi@live.cn","date":"2019-07-26T17:11:37Z"}
         * committer : {"name":"gyd","email":"guoyadi@live.cn","date":"2019-07-26T17:11:37Z"}
         * message : toolbar 代替actionbar
         * tree : {"sha":"20efa7fb76f8283b21f07ca23d230190bb74401f","url":"https://api.github.com/repos/Ninja2005/AndroidMVVM/git/trees/20efa7fb76f8283b21f07ca23d230190bb74401f"}
         * url : https://api.github.com/repos/Ninja2005/AndroidMVVM/git/commits/11f274bacfc064fe04e235b8bb9d6be4a09557f1
         * comment_count : 0
         * verification : {"verified":false,"reason":"unsigned","signature":null,"payload":null}
         */

        private AuthorBean author;
        private CommitterBean committer;
        private String message;
        private TreeBean tree;
        private String url;
        private int comment_count;
        private VerificationBean verification;

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
            this.author = author;
        }

        public CommitterBean getCommitter() {
            return committer;
        }

        public void setCommitter(CommitterBean committer) {
            this.committer = committer;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public TreeBean getTree() {
            return tree;
        }

        public void setTree(TreeBean tree) {
            this.tree = tree;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public VerificationBean getVerification() {
            return verification;
        }

        public void setVerification(VerificationBean verification) {
            this.verification = verification;
        }

        public static class AuthorBean {
            /**
             * name : gyd
             * email : guoyadi@live.cn
             * date : 2019-07-26T17:11:37Z
             */

            private String name;
            private String email;
            private String date;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }

        public static class CommitterBean {
            /**
             * name : gyd
             * email : guoyadi@live.cn
             * date : 2019-07-26T17:11:37Z
             */

            private String name;
            private String email;
            private String date;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }

        public static class TreeBean {
            /**
             * sha : 20efa7fb76f8283b21f07ca23d230190bb74401f
             * url : https://api.github.com/repos/Ninja2005/AndroidMVVM/git/trees/20efa7fb76f8283b21f07ca23d230190bb74401f
             */

            private String sha;
            private String url;

            public String getSha() {
                return sha;
            }

            public void setSha(String sha) {
                this.sha = sha;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class VerificationBean {
            /**
             * verified : false
             * reason : unsigned
             * signature : null
             * payload : null
             */

            private boolean verified;
            private String reason;
            private Object signature;
            private Object payload;

            public boolean isVerified() {
                return verified;
            }

            public void setVerified(boolean verified) {
                this.verified = verified;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public Object getSignature() {
                return signature;
            }

            public void setSignature(Object signature) {
                this.signature = signature;
            }

            public Object getPayload() {
                return payload;
            }

            public void setPayload(Object payload) {
                this.payload = payload;
            }
        }
    }

    public static class ParentsBean {
        /**
         * sha : 246f815cf91770bbb090757c19b26c4e50b73a83
         * url : https://api.github.com/repos/Ninja2005/AndroidMVVM/commits/246f815cf91770bbb090757c19b26c4e50b73a83
         * html_url : https://github.com/Ninja2005/AndroidMVVM/commit/246f815cf91770bbb090757c19b26c4e50b73a83
         */

        private String sha;
        private String url;
        private String html_url;

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }
    }
}
