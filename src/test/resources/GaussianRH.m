function [out] = GaussianRH(sigma,S,D)
%GAUSSIANRH Function to make 1D 2D or 3D gaussians. Peak at center
%   sigma: - sigma in x y and z
%       S: - image size
%       D: - nr of dimensions
if nargin<3||isempty(D),D = length(S);end

switch D
    case 1
        S=S-1;
        f = 1/sqrt(2*pi*sigma^2);
        x = [-S/2:1:S/2];
        out = f*exp(-x.^2/(2*sigma^2));
    case 2
        S=S-1;
        f = 1/(2*pi*sigma^2);
        x = [-S/2:1:S/2];
        x = exp(-x.^2/(2*sigma^2));
        out = f*(x'*x);
    case 3
        S=S-1;
        f = 1/(2*pi*sigma^2)^(3/2);
        x = [-S/2:1:S/2];
        x = exp(-x.^2/(2*sigma^2));
        out = f*(x.*x'.*permute(x,[3,1,2]));
end

end

