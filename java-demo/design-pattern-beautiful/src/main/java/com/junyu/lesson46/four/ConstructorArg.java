package com.junyu.lesson46.four;

/**
 * @author haojunsheng
 * @date 2021/4/15 23:31
 */
public class ConstructorArg {
    private boolean isRef;
    private Class type;
    private Object arg;

    public boolean isRef() {
        return isRef;
    }

    public Class getType() {
        return type;
    }

    public Object getArg() {
        return arg;
    }

    private ConstructorArg(Builder builder) {
        this.isRef = builder.isRef;
        this.arg = builder.arg;
        this.type = builder.type;
    }

    @Override
    public String toString() {
        return "ConstructorArg{" +
                "isRef=" + isRef +
                ", type=" + type +
                ", arg=" + arg +
                '}';
    }

    public static class Builder {
        private boolean isRef;
        private Class type;
        private Object arg;

        public ConstructorArg build() {
            if (isRef && type != null) {
                throw new IllegalArgumentException("...");
            }

            if (!isRef && type == null) {
                throw new IllegalArgumentException("...");
            }

            if (this.isRef && (arg != null && arg.getClass() != String.class)) {
                throw new IllegalArgumentException("...");
            }

            if (!this.isRef && arg == null) {
                throw new IllegalArgumentException("...");
            }
            return new ConstructorArg(this);
        }

        public Builder setRef(boolean ref) {
            this.isRef = ref;
            return this;
        }

        public Builder setType(Class type) {
            if (type == null) {
                throw new IllegalArgumentException("...");
            }
            this.type = type;
            return this;
        }

        public Builder setArg(Object arg) {
            if (arg == null) {
                throw new IllegalArgumentException("...");
            }
            this.arg = arg;
            return this;
        }
    }
}
