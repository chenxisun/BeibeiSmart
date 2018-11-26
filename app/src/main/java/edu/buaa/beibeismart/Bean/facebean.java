package edu.buaa.beibeismart.Bean;

import java.util.List;

public class facebean {
    /**
     * image_id : dh1YjLbv5nWeGJwMMAo3Jw==
     * request_id : 1540137113,8cc0936f-dda8-4084-bb06-1d03d276233e
     * time_used : 484
     * faces : [{"attributes":{"emotion":{"sadness":28.198,"neutral":68.967,"disgust":0.024,"anger":1.947,"surprise":0.52,"fear":0.024,"happiness":0.319}},"face_rectangle":{"width":478,"top":273,"left":675,"height":478},"face_token":"f906480914a6aeee9b1845e6e62bc514"}]
     */

    private String image_id;
    private String request_id;
    private int time_used;
    private List<FacesBean> faces;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public List<FacesBean> getFaces() {
        return faces;
    }

    public void setFaces(List<FacesBean> faces) {
        this.faces = faces;
    }

    public static class FacesBean {
        /**
         * attributes : {"emotion":{"sadness":28.198,"neutral":68.967,"disgust":0.024,"anger":1.947,"surprise":0.52,"fear":0.024,"happiness":0.319}}
         * face_rectangle : {"width":478,"top":273,"left":675,"height":478}
         * face_token : f906480914a6aeee9b1845e6e62bc514
         */

        private AttributesBean attributes;
        private FaceRectangleBean face_rectangle;
        private String face_token;

        public AttributesBean getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBean attributes) {
            this.attributes = attributes;
        }

        public FaceRectangleBean getFace_rectangle() {
            return face_rectangle;
        }

        public void setFace_rectangle(FaceRectangleBean face_rectangle) {
            this.face_rectangle = face_rectangle;
        }

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }

        public static class AttributesBean {
            /**
             * emotion : {"sadness":28.198,"neutral":68.967,"disgust":0.024,"anger":1.947,"surprise":0.52,"fear":0.024,"happiness":0.319}
             */

            private EmotionBean emotion;

            public EmotionBean getEmotion() {
                return emotion;
            }

            public void setEmotion(EmotionBean emotion) {
                this.emotion = emotion;
            }

            public static class EmotionBean {
                /**
                 * sadness : 28.198
                 * neutral : 68.967
                 * disgust : 0.024
                 * anger : 1.947
                 * surprise : 0.52
                 * fear : 0.024
                 * happiness : 0.319
                 */

                private double sadness;
                private double neutral;
                private double disgust;
                private double anger;
                private double surprise;
                private double fear;
                private double happiness;

                public double getSadness() {
                    return sadness;
                }

                public void setSadness(double sadness) {
                    this.sadness = sadness;
                }

                public double getNeutral() {
                    return neutral;
                }

                public void setNeutral(double neutral) {
                    this.neutral = neutral;
                }

                public double getDisgust() {
                    return disgust;
                }

                public void setDisgust(double disgust) {
                    this.disgust = disgust;
                }

                public double getAnger() {
                    return anger;
                }

                public void setAnger(double anger) {
                    this.anger = anger;
                }

                public double getSurprise() {
                    return surprise;
                }

                public void setSurprise(double surprise) {
                    this.surprise = surprise;
                }

                public double getFear() {
                    return fear;
                }

                public void setFear(double fear) {
                    this.fear = fear;
                }

                public double getHappiness() {
                    return happiness;
                }

                public void setHappiness(double happiness) {
                    this.happiness = happiness;
                }
            }
        }

        public static class FaceRectangleBean {
            /**
             * width : 478
             * top : 273
             * left : 675
             * height : 478
             */

            private int width;
            private int top;
            private int left;
            private int height;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}
